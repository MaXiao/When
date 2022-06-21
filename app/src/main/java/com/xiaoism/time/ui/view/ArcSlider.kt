package com.xiaoism.time.ui.view

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.lang.Math.toRadians
import kotlin.math.*

private const val hMargin = 10f
private const val topMargin = 80f
private const val angleMargin = 45f

@Composable
fun ArcSlider(modifier: Modifier = Modifier, value: Float, onValueChanged: (Float) -> Unit) {
    val configuration = LocalConfiguration.current
    val density = LocalContext.current.resources.displayMetrics.density
    val screenWidth = configuration.screenWidthDp * density
    val arcRadius = screenWidth * 1.5f / 2
    val horizontalCenter = screenWidth / 2
    val verticalCenter = arcRadius + topMargin * density
    val startAngle = 180f + angleMargin
    val span = 180f - angleMargin * 2
    var angle by remember {
        mutableStateOf(
            calculateIndicatorAngleByPercent(
                startAngle,
                span,
                value
            )
        )
    }
    val radial = Brush.radialGradient(
        0.0f to Color(215, 103, 170, 255),
        0.66f to Color(237, 134, 83, 60),
        1.0f to Color(255, 159, 14, 0),
        center = Offset(horizontalCenter, verticalCenter - topMargin * density),
        radius = arcRadius * 1.1f,
        tileMode = TileMode.Clamp
    )
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(density * 2f, density * 3f), 0f)
    Canvas(modifier = modifier
        .background(radial)
        .pointerInput(Unit) {
            detectDragGestures { change, _ ->
                change.consumeAllChanges()
                val dragXOnCanvas = change.position.x - horizontalCenter
                val dragYOnCanvas =
                    max(
                        abs(change.position.y - verticalCenter),
                        arcRadius * sin(toRadians(angleMargin.toDouble())).toFloat()
                    )
                angle = calculateIndicatorAngleByPosition(dragXOnCanvas, dragYOnCanvas)
            }
            detectTapGestures { offset ->
                Log.d("canvas", offset.toString())
            }
        }) {
        val indicatorX = arcRadius * cos(angle)
        val indicatorY = arcRadius * sin(angle)
        val indicatorDegree = Math.toDegrees(angle.toDouble())
        val ratio = (indicatorDegree - startAngle) / span

        onValueChanged(min(max(ratio.toFloat(), 0f), 1f))

        drawArc(
            color = Color.Black,
            startAngle = startAngle,
            sweepAngle = span,
            useCenter = false,
            style = Stroke(width = 2.dp.toPx(), pathEffect = pathEffect),
            topLeft = Offset(-arcRadius / 3, topMargin.dp.toPx()),
            size = Size(arcRadius * 2, arcRadius * 2)
        )

        translate(indicatorX, indicatorY) {
            drawCircle(
                color = Color.Magenta.copy(alpha = 0.4f),
                radius = hMargin.dp.toPx(),
                center = Offset(x = size.width / 2f, y = topMargin.dp.toPx() + arcRadius),
                style = Stroke(width = 6.dp.toPx())
            )
        }
    }
}

private fun radiusForPoint(x: Float, y: Float): Float {
    return sqrt(x.pow(2) + y.pow(2))
}

private fun calculateIndicatorAngleByPosition(x: Float, y: Float): Float {
    val radius = radiusForPoint(x, y)
    val angle = acos(x / radius)
    return 2 * PI.toFloat() - angle
}

private fun calculateIndicatorAngleByPercent(
    startAngle: Float,
    span: Float,
    percent: Float
): Float {
    return toRadians(span * percent.toDouble() + startAngle.toDouble()).toFloat()
}



