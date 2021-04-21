package com.aakarshrestha.swipetorefresh

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

@Composable
internal fun ProgressIndicator(progress: Float, indicatorColor: Color = MaterialTheme.colors.primary) {

    val arcWidth = 2.5f
    val arcDiameter = arcWidth/2

    val path = Path().apply {
        moveTo(x = 0f, y = progress * 27f)
        lineTo(x = -progress * 20f, y = progress * 55f)
        lineTo(x = progress * 20f, y = progress * 55f)
        close()
    }

    Box(
        modifier = Modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            withTransform({
                translate(top = 120f - progress * 80f, left = size.width/2)
                rotate(
                    degrees = progress * 0.45f * 360,
                    pivot = Offset(x = -progress * 14f, y = 0f)
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
                style = Stroke(arcWidth * 3, cap = StrokeCap.Butt),
                topLeft = Offset(x = arcDiameter, y = arcDiameter),
                size = Size(width = size.width - 2 * arcDiameter, height = size.width - 2 * arcDiameter)
            )

        }
    }
}