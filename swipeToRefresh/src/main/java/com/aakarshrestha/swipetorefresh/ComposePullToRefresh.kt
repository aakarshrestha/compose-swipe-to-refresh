package com.aakarshrestha.swipetorefresh

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private const val INDICATOR_REFRESH_MAX_OFFSET = 400f

/**
 * Creates a [ComposePullToRefreshState] that is remembered across compositions.
 *
 * Changes to [isRefreshing] will result in the [ComposePullToRefreshState] being updated.
 *
 * @param isRefreshing the value for [ComposePullToRefreshState.isRefreshing]
 */
@Composable
fun rememberComposePullToRefreshState(
    isRefreshing: Boolean
): ComposePullToRefreshState {
    return remember {
        ComposePullToRefreshState(isRefreshing)
    }.apply {
        this.isRefreshing.value = isRefreshing
    }
}

/**
 * This class maintains the state of the pull to refresh.
 *
 * @param isRefreshing the initial value for pull to refresh.
 */
class ComposePullToRefreshState(isRefreshing: Boolean) {

    //_indicatorOffset is used for animating pull to refresh indicator to the given position
    private val _indicatorOffset = Animatable(0f)
    //mutatorMutex makes sure that there is only one active writer at a given time.
    private val mutatorMutex = MutatorMutex()

    //isRefreshing stores the refreshing state
    var isRefreshing = mutableStateOf(isRefreshing)

    //isSwiping stores the swiping state
    var isSwiping = mutableStateOf(false)

    //indicatorOffset is used to store the value of dragged pull to refresh indicator
    // its value can go up to INDICATOR_REFRESH_MAX_OFFSET times 2
    val indicatorOffset: Float get() = _indicatorOffset.value.coerceAtMost(INDICATOR_REFRESH_MAX_OFFSET * 2)

    //indicatorToRefreshOffset defined the max value to indicate the refresh will happen
    var indicatorToRefreshOffset = mutableStateOf(INDICATOR_REFRESH_MAX_OFFSET)
        internal set

    //a method to upload value in _indicatorOffset
    internal suspend fun updateIndicator(delta: Float) {
        mutatorMutex.mutate(MutatePriority.UserInput) {
            _indicatorOffset.snapTo(_indicatorOffset.value + delta)
        }
    }

    //this method makes sure that of the refreshing is happening then place the pull to refresh indicator
    //to the given position.
    internal suspend fun animateBackToRest() {
        mutatorMutex.mutate {
            _indicatorOffset.animateTo(if (isRefreshing.value) indicatorToRefreshOffset.value else 0f)
        }
    }
}


/**
 * [ComposePullToRefreshNestedScrollCompletion] reacts to the scroll either up or down.
 *
 * @param state of the pull to refresh indicator
 * @param scope coroutine to run the suspend method
 * @param onRefresh callback method
 */
class ComposePullToRefreshNestedScrollCompletion(
    private val state: ComposePullToRefreshState,
    private val scope: CoroutineScope,
    private val onRefresh: () -> Unit
): NestedScrollConnection {
    var enabled: Boolean = false

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset = when {

        //when enabled is false, return Offset zero
        !enabled -> Offset.Zero
        //When the source is drag and value is less than 0, do something
        source == NestedScrollSource.Drag && available.y < 0 -> {
            onScroll(available)
        }
        //otherwise, return offset zero
        else -> Offset.Zero

    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {

        //when enabled is false, return Offset zero
        !enabled -> Offset.Zero
        //When the source is drag and value is greater than 0, do something
        source == NestedScrollSource.Drag && available.y > 0 -> {
            onScroll(available)
        }
        //otherwise, return offset zero
        else -> Offset.Zero

    }

    private fun onScroll(available: Offset): Offset {
        //set isSwiping value to true
        state.isSwiping.value = true

        val delta = available.y
        val newOffset = (state.indicatorOffset + delta).coerceAtLeast(0f) //minimum value of newOffset to be at least 0

        return if(newOffset.absoluteValue >= 0.5f) {
            //upload the delta value in indicatorOffset
            scope.launch {
                state.updateIndicator(delta)
            }
            //return the available y value
            Offset(x = 0f, y = delta)
        }
        else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity = when {

        //if dragged to the refreshing point and isSwiping is true, invoke onRefresh callback method
        state.isSwiping.value && state.indicatorOffset >= state.indicatorToRefreshOffset.value -> {

            onRefresh()

            Velocity.Zero
        }

        else -> {
            Velocity.Zero
        }
    }.also {
        state.isSwiping.value = false
    }
}


/**
 * [ComposePullToRefresh] implements pull to refresh action.
 *
 * @param isRefreshing initial value for pull to refresh
 * @param onRefresh callback method which get triggered when pull to refresh indicator is pull down to a given position
 * @param indicatorTopPadding padding for the pull to refresh indicator
 * @param indicatorColor color to display in the progress bar
 * @param content scrollable composable to be placed here
 *
 */
@Composable
fun ComposePullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    indicatorTopPadding: Dp = 60.dp,
    indicatorColor: Color = MaterialTheme.colors.primary,
    content: @Composable () -> Unit
) {

    val scope = rememberCoroutineScope()

    val state = rememberComposePullToRefreshState(isRefreshing = isRefreshing)
    state.isRefreshing.value = isRefreshing

    //if any of the values of the params in LaunchedEffect changes then only code
    //inside LaunchedEffect block will be executed otherwise it won't.
    LaunchedEffect(!state.isRefreshing.value, state.isSwiping.value, state.indicatorToRefreshOffset) {
        if (!state.isSwiping.value) {
            //if isSwiping is false, then set the indicator to 0f
            state.animateBackToRest()
        }
    }

    val connection = remember(state, scope) {
        ComposePullToRefreshNestedScrollCompletion(state, scope) {
            onRefresh()
        }
    }.apply {
        //when refreshing is true then do not allow to pull to refresh
        // otherwise allow to pull to refresh.
        this.enabled = !state.isRefreshing.value
    }

    Box (
        modifier = Modifier
            .nestedScroll(connection)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        val cardSize = (state.indicatorOffset * 0.2f).coerceIn(0f, 40f).roundToInt()

        content()

        Card(
            modifier = Modifier
                .padding(top = indicatorTopPadding)
                .offset {
                    IntOffset(x = 0, y = state.indicatorOffset.roundToInt().div(2))
                }
                .size(cardSize.dp),
            elevation = 5.dp,
            shape = CircleShape
        ) {

            if (state.isRefreshing.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(7.dp),
                    strokeWidth = 2.dp,
                    color = indicatorColor
                )
            } else {
                ProgressIndicator(
                    progress = state.indicatorOffset.roundToInt().div(2).div(530f),
                    indicatorColor = indicatorColor
                )
            }

        }
    }
}