package com.xiaoism.time.ui.main.group

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.PersonWithCity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToInt

class GroupActivity : ComponentActivity() {
    private val minPerHour = 60
    private val minPerDay = 24 * minPerHour
    private val date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val group: GroupWithPersons? = intent.getParcelableExtra("group") as? GroupWithPersons
        group?.let {
            setContent {
                content(group = it)
            }
        }

    }

    @Composable
    private fun content(group: GroupWithPersons) {
        var sliderPosition by remember { mutableStateOf(0f) }
        val date = convertTime(sliderPosition.roundToInt() * 5)

        Column() {
            Text(
                "${group.group.name}",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
            LazyColumn {
                items(group.persons) { person ->
                    row(person, date)
                    Divider(color = Color.Gray, thickness = 6.dp)
                }
            }

            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 0f..(minPerDay / 5).toFloat(),
                steps = 24,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colors.secondary,
                    activeTrackColor = MaterialTheme.colors.secondary
                )
            )
            Text(text = date.toString())
        }
    }

    @Composable
    private fun row(person: PersonWithCity, date:Date) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(person.person.name, fontSize = 16.sp)
            if (person.city != null) {
                Text(
                    "${person.city.getLocalTimeFor(date)}",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            } else null
        }
    }

    private fun convertTime(mins: Int): Date {
        Log.d("time", mins.toString())
        val hour = mins / minPerHour
        val min = mins % minPerHour
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, min)
        return cal.time
    }
}