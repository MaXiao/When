package com.xiaoism.time.ui.main.view

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
private const val vMargin = 40f
private const val angleMargin = 20f

@Composable
fun ArcSlider(modifier: Modifier = Modifier, value: Float, onValueChanged: (Float) -> Unit) {
    val configuration = LocalConfiguration.current
    val density = LocalContext.current.resources.displayMetrics.density
    val screenWidth = configuration.screenWidthDp * density
    val arcRadius = screenWidth / 2 - hMargin * density
    val horizontalCenter = screenWidth / 2
    val verticalCenter = arcRadius + vMargin * density
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
        0.0f to Color(31, 80, 255, 255),
        0.56f to Color(18, 216, 250, 56),
        1.0f to Color(184, 255, 166, 0),
        center = Offset(horizontalCenter, verticalCenter),
        radius = arcRadius,
        tileMode = TileMode.Clamp
    )

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
        Log.e("render", "$indicatorDegree, $ratio")
        onValueChanged(min(max(ratio.toFloat(), 0f), 1f))

        drawArc(
            color = Color.Blue,
            startAngle = startAngle,
            sweepAngle = span,
            useCenter = false,
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(hMargin.dp.toPx(), vMargin.dp.toPx()),
            size = Size(arcRadius * 2, arcRadius * 2)
        )

        translate(indicatorX, indicatorY) {
            drawCircle(
                color = Color.Magenta.copy(alpha = 0.4f),
                radius = hMargin.dp.toPx(),
                center = Offset(x = size.width / 2f, y = arcRadius + vMargin.dp.toPx()),
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



