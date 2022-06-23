package com.xiaoism.time.ui.group.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xiaoism.time.R
import com.xiaoism.time.model.PersonWithCity
import java.util.*

@Composable
fun PersonGrid(person: PersonWithCity, date: Date) {
    val isDayTime = person.city?.isDayTime(date) ?: true
    val density = LocalContext.current.resources.displayMetrics.density
    var dashLength = animateFloatAsState(targetValue = if (isDayTime) 5f else 2f)
    var dashGap = animateFloatAsState(targetValue = if (isDayTime) 0f else 3f)
    var borderRotate = animateFloatAsState(targetValue = if (isDayTime) 0f else 90f)
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(
                dashLength.value * density,
                dashGap.value * density
            ), 0f
        )
    )

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 0.dp, top = 6.dp)
    ) {
        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(2.dp)
                .height(100.dp)
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(56.dp)
                    .padding(bottom = 12.dp)
            ) {
                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .rotate(borderRotate.value)) {
                    drawCircle(color = Color.Black, style = stroke)
                }
                Image(
                    painter = painterResource(id = if (isDayTime) R.drawable.ic_day else R.drawable.ic_evening),
                    contentDescription = "day/night icon",
                    modifier = Modifier
                        .padding(11.dp)
                        .width(22.dp)
                        .height(22.dp)
                )
            }
            Text(
                person.person.name,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            if (person.city != null) {
                val diff = person.city.getDayDiff(date)
                Text(
                    person.city.getLocalTimeFor(date),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row {
                    Text(
                        "${person.city.name}, ${person.city.country}",
                        style = MaterialTheme.typography.subtitle1
                    )
                    if (diff != 0) {
                        Text(
                            "${if (diff > 0) '+' else ' '}${diff}d",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            Divider(modifier = Modifier.height(24.dp), color = Color.Transparent)
        }
    }
}