package com.xiaoism.time.ui.main.view

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import java.lang.Math.*
import java.lang.Math.sin
import kotlin.math.*

@Composable
fun ArcSlider(modifier: Modifier = Modifier) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Canvas(modifier = modifier.pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            change.consumeAllChanges()
            offsetX = change.position.x
            offsetY = change.position.y

            Log.d("canvas", change.position.toString())
        }
        detectTapGestures { offset ->
            Log.d("canvas", offset.toString())
            offsetX = offset.x
            offsetY = offset.y
        }
    }) {
        val (indicatorX, indicatorY) = calculateIndicatorPosition(offsetX, offsetY)
        Log.d("updated", indicatorY.toString())
        drawArc(
            color = Color.Blue,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(10.dp.toPx(), 40.dp.toPx()),
            size = Size(arcRadius * 2, arcRadius * 2)
        )

        translate(indicatorX, indicatorY) {
            drawCircle(
                color = Color.Magenta.copy(alpha = 0.4f),
                radius = 10.dp.toPx(),
                center = Offset(x = size.width / 2f, y = arcRadius + 40.dp.toPx()),
                style = Stroke(width = 6.dp.toPx())
            )
        }
    }
}

private fun radiusForPoint(x: Float, y: Float): Float {
    return sqrt(x.pow(2) + y.pow(2)).toFloat()
}

private fun DrawScope.calculateIndicatorPosition(offsetX: Float, offsetY: Float): Offset {
    val dragXOnCanvas = offsetX - horizontalCenter
    val dragYOnCanvas = Math.min(offsetY - verticalCenter, 0f)
    val radius = radiusForPoint(dragXOnCanvas, dragYOnCanvas)
    val angle = acos(dragXOnCanvas / radius)
    val adjustedAngle = if (dragYOnCanvas < 0) angle * -1 else angle
    val xOnCircle = arcRadius * cos(adjustedAngle)
    val yOnCircle = arcRadius * sin(adjustedAngle)
    return Offset(xOnCircle.toFloat(), yOnCircle.toFloat())
}

private val DrawScope.arcRadius get() = size.width / 2 - 10.dp.toPx()
private val DrawScope.horizontalCenter get() = size.width / 2
private val DrawScope.verticalCenter get() = arcRadius + 40.dp.toPx()