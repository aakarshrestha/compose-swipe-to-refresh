package com.aakarshrestha.swipetorefresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val BASE_OFFSET = 400f
private const val MAX_OFFSET = 1000f
private const val MID_OFFSET = 270f

/**
 *
 * @param isRefreshing
 * @param progressBarColor
 * @param pullToRefreshTextColor
 * @param refreshSectionBackgroundColor
 * @param onRefresh
 * @param content
 */
@Composable
fun SwipeToRefresh(
    isRefreshing: Boolean,
    progressBarColor: Color? = null,
    pullToRefreshTextColor: Color? = null,
    refreshSectionBackgroundColor: Color? = null,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {

    val boxOneOffset = remember { mutableStateOf(0f) }
    val dragOffset = remember { mutableStateOf(0f) }
    val refreshTextValue = remember { mutableStateOf("Pull to refresh") }
    val refreshState = remember { mutableStateOf(isRefreshing) }

    val connection = object : NestedScrollConnection {
        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {

            val dragDelta = available.y
            val dragNewOffset = dragOffset.value + dragDelta
            dragOffset.value = dragNewOffset

            if (source == NestedScrollSource.Drag && !refreshState.value) {
                when {
                    dragOffset.value >= BASE_OFFSET && dragOffset.value < MAX_OFFSET -> {
                        val delta = available.y
                        val newOffset = boxOneOffset.value + delta
                        boxOneOffset.value = newOffset.coerceIn(0f, BASE_OFFSET)
                    }
                    dragOffset.value >= MAX_OFFSET -> {
                        refreshState.value = true
                        refreshTextValue.value = "Refreshing..."
                        onRefresh.invoke()
                    }
                }
            }

            return super.onPostScroll(consumed, available, source)
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            if (refreshState.value) {
                boxOneOffset.value = MID_OFFSET
                return Velocity.Zero
            }

            boxOneOffset.value = 0f
            dragOffset.value = 0f
            refreshTextValue.value = "Pull to refresh"

            return super.onPostFling(consumed, available)
        }
    }

    Box(
        modifier = Modifier
            .nestedScroll(connection)
            .background(color = refreshSectionBackgroundColor ?: Color.White),
        contentAlignment = Alignment.TopCenter
    ) {

        RefreshSection(isRefreshing, refreshTextValue, progressBarColor, pullToRefreshTextColor)

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x=0, y=boxOneOffset.value.roundToInt())
                }
                .fillMaxSize()
        ) {
            content.invoke()


            if (!isRefreshing) {
                boxOneOffset.value = 0f
                dragOffset.value = 0f
                refreshTextValue.value = "Pull to refresh"
                refreshState.value = false
            }
        }
    }
}

/**
 *
 *
 * @param isRefreshing
 * @param refreshTextValue
 * @param progressBarColor
 * @param pullToRefreshTextColor
 */
@Composable
private fun RefreshSection(
    isRefreshing: Boolean,
    refreshTextValue: MutableState<String>,
    progressBarColor: Color? = null,
    pullToRefreshTextColor: Color? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (isRefreshing) {
                CircularProgressIndicator(modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                    color = progressBarColor ?: Color.LightGray,
                    strokeWidth = 1.dp
                )

                Spacer(modifier = Modifier.width(10.dp))
            }

            Text(
                text = refreshTextValue.value,
                color = pullToRefreshTextColor ?: Color.LightGray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}