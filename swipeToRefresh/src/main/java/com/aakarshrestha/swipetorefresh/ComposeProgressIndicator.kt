package com.aakarshrestha.swipetorefresh

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
internal fun ProgressIndicator(progress: Float, indicatorColor: Color = MaterialTheme.colors.primary) {

    val progressIndicatorStrokeWidth = ProgressIndicatorDefaults.StrokeWidth
    val progressIndicatorStrokeWidthPx = with(LocalDensity.current) {
        progressIndicatorStrokeWidth.toPx()/2
    }

    val pathWidth = 3 * progressIndicatorStrokeWidthPx * progress
    val stroke = Stroke(progressIndicatorStrokeWidthPx)
    val arcDiameter = stroke.width / 2

    val path = Path().apply {
        moveTo(0f, -pathWidth * progress * 1.5F)
        lineTo(pathWidth * progress  * 1.5F, 0f)
        lineTo(0f, pathWidth * progress  * 1.5F)
        close()
    }

    Box {
        Canvas(modifier = Modifier
            .rotate(progress * 0.5f * 2f * 360)
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            ) {
                withTransform({
                    translate(top = progressIndicatorStrokeWidthPx.div(2), left = size.width.div(2))
                    rotate(
                        degrees = progress * 360,
                        pivot = Offset(x = 0f, y = size.height.div(2) - arcDiameter)
                    )
                }) {
                    drawPath(
                        path = path,
                        color = indicatorColor,
                        alpha = if (progress >= 0.75) 1f else progress
                    )
                }

                drawArc(
                    color = indicatorColor,
                    startAngle = -90f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    alpha = if (progress >= 0.75) 1f else progress,
                    topLeft = Offset(x = arcDiameter, y = arcDiameter),
                    size = Size(width = size.width - 2 * arcDiameter, height = size.width - 2 * arcDiameter),
                    style = stroke
                )
        }
    }
}