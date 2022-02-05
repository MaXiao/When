package com.xiaoism.time.ui.main.group

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.PersonWithCity
import androidx.compose.runtime.livedata.observeAsState
import com.xiaoism.time.model.Group
import com.xiaoism.time.ui.main.people.PersonSelectionActivity
import com.xiaoism.time.ui.main.people.PersonSelectionFragment
import com.xiaoism.time.ui.main.people.PersonsSelectActivityContract
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import androidx.activity.result.ActivityResultCallback

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.background
import com.xiaoism.time.ui.main.view.ArcSlider
import java.util.jar.Manifest


@AndroidEntryPoint
class GroupActivity : ComponentActivity() {
    private val viewModel by viewModels<GroupViewModel>()
    private val currentDate = Date()
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cal.time = currentDate

        val groupId = intent.getLongExtra("group", -1)
        if (groupId >= 0) {
            viewModel.configGroup(groupId)
        }

        setContent {
            Scaffold(
                content = { Content() },
                floatingActionButton = { AddBtn() }
            )
        }
    }

    //region Views
    @Composable
    private fun AddBtn() {
        FloatingActionButton(onClick = { addPerson() }) {
            Icon(Icons.Filled.Add, "")
        }
    }

    @Composable
    private fun Content() {
        val group by viewModel.group.observeAsState(
            initial = GroupWithPersons(
                group = Group(name = ""),
                persons = emptyList()
            )
        )
        val (sliderPosition, setSliderPosition) = remember { mutableStateOf(0f) }
        val (date, setDate) = remember { mutableStateOf(cal.time) }

        val datePicker =
            DatePickerDialog(this, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                Log.d("date", "$year/$month/$dayOfMonth")
                val localCal = Calendar.getInstance()
                localCal.time = date
                localCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                localCal.set(Calendar.MONTH, month)
                localCal.set(Calendar.YEAR, year)
                setDate(localCal.time)
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

        Column {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    group.group.name,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .weight(1f)
                )

                OutlinedButton(onClick = { editGroup(group.group.groupId) }) {
                    Text("Edit")
                }
            }
            LazyColumn(Modifier.weight(1f)) {
                items(group.persons.sortedBy { it.person.name.lowercase(Locale.getDefault()) }) { person ->
                    Row(person, date)
                    Divider(color = Color.Gray, thickness = 6.dp)
                }
            }

            Slider(sliderPosition, setSliderPosition, date, setDate)
            Text(text = date.toString())
            Spacer(modifier = Modifier.height(20.dp))
            ArcSlider(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.1f)), value = 0.3f
            ) { ratio ->
                val mins = ((MIN_PER_DAY / 5) * ratio).roundToInt() * 5;
                val date = convertTime(((MIN_PER_DAY / 5) * ratio).roundToInt() * 5, date)
                setDate(date)
            }
            OutlinedButton(onClick = { datePicker.show() }) {
                Text("Date")
            }
            OutlinedButton(onClick = { shareCalendarEvent(date.time) }) {
                Text("Share")
            }
        }
    }

    @Composable
    private fun Slider(
        sliderPosition: Float,
        setSliderPosition: (Float) -> Unit,
        date: Date,
        setDate: (Date) -> Unit
    ) {
        Slider(
            value = sliderPosition,
            onValueChange = {
                setSliderPosition(it)
                setDate(convertTime(sliderPosition.roundToInt() * 5, date))
            },
            valueRange = 0f..(MIN_PER_DAY / 5).toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.secondary
            )
        )
    }

    @Composable
    private fun Row(person: PersonWithCity, date: Date) {
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
                    person.city.getLocalTimeFor(date),
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }
    }
    //endregion

    //region Methods
    private fun addPerson() {
        selectMembers.launch(
            PersonsSelectActivityContract.PersonSelectInput(
                false,
                viewModel.group.value?.persons
            )
        )
    }

    private val selectMembers =
        registerForActivityResult(PersonsSelectActivityContract()) { list ->
            list?.let {
                if (it.isNotEmpty()) {
                    viewModel.addMember(it[0].person)
                }
            }
        }

    private fun convertTime(mins: Int, inputDate: Date): Date {
        val hour = mins / MIN_PER_HOUR
        val min = mins % MIN_PER_HOUR
        val cal = Calendar.getInstance()
        cal.time = inputDate
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, min)
        cal.set(Calendar.SECOND, 0)
        return cal.time
    }

    private fun editGroup(groupId: Long) {
        val intent = Intent(this, CreateGroupActivity::class.java)
        intent.putExtra(CreateGroupActivity.GROUP_ID, groupId)
        startActivity(intent)
    }

    private fun shareCalendarEvent(startMillis: Long) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, "game night")
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startMillis + 3000)
//            putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com")
        }

        val packages = intent.resolveActivity(packageManager)
        if (packages != null) {
            startActivity(intent);
        }
    }
//endregion

    companion object {
        const val MIN_PER_HOUR = 60
        const val MIN_PER_DAY = MIN_PER_HOUR * 24
    }
}