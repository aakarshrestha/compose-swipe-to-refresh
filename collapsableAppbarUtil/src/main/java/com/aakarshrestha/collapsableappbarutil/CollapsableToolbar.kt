package com.aakarshrestha.collapsableappbarutil

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import kotlin.math.roundToInt

@Composable
fun CollapsableToolbar(
    isBottomAppBarVisible: Boolean? = true,
    contentToAddInTopAppBar: @Composable () -> Unit,
    body: @Composable () -> Unit,
    contentToAddInBottomAppBar: @Composable () -> Unit = {}
) {
    val toolbarHeight = 56.dp
    val toolbarHeightToPx = with(LocalDensity.current) {
        toolbarHeight.roundToPx().toFloat()
    }

    val toolbarOffsetHeightToPx = remember { mutableStateOf(0f) }

    val connection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightToPx.value + delta
                toolbarOffsetHeightToPx.value = newOffset.coerceIn(-toolbarHeightToPx, 0f)
                return super.onPreScroll(available, source)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(connection)
    ) {
        body.invoke()

        TopAppBar(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightToPx.value.roundToInt()) }
                .height(toolbarHeight)
        ) {
            contentToAddInTopAppBar.invoke()
        }

        if (isBottomAppBarVisible == true) {
            BottomAppBar(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .height(toolbarHeight)
                    .offset { IntOffset(x = 0, y = -toolbarOffsetHeightToPx.value.roundToInt()) }
            ) {
                contentToAddInBottomAppBar.invoke()
            }
        }
    }
}