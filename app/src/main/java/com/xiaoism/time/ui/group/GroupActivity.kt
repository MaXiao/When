package com.xiaoism.time.ui.group

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.xiaoism.time.ui.person.PersonsSelectActivityContract
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.roundToInt

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.xiaoism.time.R
import com.xiaoism.time.ui.group.components.PersonGrid
import com.xiaoism.time.ui.theme.Typography
import com.xiaoism.time.ui.view.ArcSlider
import java.text.SimpleDateFormat


@AndroidEntryPoint
class GroupActivity : ComponentActivity() {
    private val viewModel by viewModels<GroupViewModel>()
    private val currentDate = Date()
    private val cal = Calendar.getInstance()
    val timeFormat = SimpleDateFormat(
        "hh:mma",
        Locale.getDefault()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cal.time = currentDate

        val groupId = intent.getLongExtra("group", -1)
        if (groupId >= 0) {
            viewModel.configGroup(groupId)
        }

        setContent {
            MaterialTheme(typography = Typography) {
                Scaffold(
                    content = { Content() },
                    floatingActionButton = { AddBtn() }
                )
            }
        }
    }

    //region Views
    @Composable
    private fun AddBtn() {
        FloatingActionButton(onClick = { addPerson() }) {
            Icon(Icons.Filled.Add, "")
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
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
        var backgroundChanged = remember { mutableStateOf(false) }
        val color by animateColorAsState(
            if (backgroundChanged.value) Color.Yellow else Color.Gray,
            animationSpec = tween(
                durationMillis = 1000
            )

        )
        val totalCount = group.persons.size
        val availableCount =
            group.persons.filter { person -> person.city?.isDayTime(date) ?: false }.size

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

        Column(modifier = Modifier.fillMaxHeight()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(vertical = 40.dp)
            ) {
                Text(
                    group.group.name,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .weight(1f)
                )

                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "share icon",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .width(32.dp)
                            .height(32.dp)
                            .clickable { shareCalendarEvent(0) }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_more_options),
                        contentDescription = "share icon",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .width(32.dp)
                            .height(32.dp)
                            .clickable { editGroup(group.group.groupId) }
                    )
                }
            }
            LazyVerticalGrid(cells = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
                items(group.persons.sortedBy { it.person.name.lowercase(Locale.getDefault()) }) { person ->
                    PersonGrid(person, date)
                }
            }
            Divider(color = Color.Black, modifier = Modifier.height(1.dp))
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                val (available, time, note) = createRefs()

                ArcSlider(
                    modifier = Modifier
                        .background(color.copy(alpha = 0.1f))
                        .height(200.dp)
                        .fillMaxWidth(), value = 0.3f
                ) { ratio ->
                    val mins = ((MIN_PER_DAY / 5) * ratio).roundToInt() * 5
                    // end of time picker is exclusive, since it goes to next day
                    val adjustedMins = if (mins == MIN_PER_DAY) mins - 5 else mins
                    val d = convertTime(adjustedMins, date)
                    setDate(d)
                }
                Text(
                    "$availableCount/$totalCount Available",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.constrainAs(available) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top, margin = 20.dp)
                    })
                Text(
                    text = timeFormat.format(date),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.constrainAs(time) {
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    })
                Text(
                    "Your local time",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.constrainAs(note) {
                        top.linkTo(time.bottom, margin = 4.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
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
        val startMillis: Long = Calendar.getInstance().run {
            set(2022, 0, 19, 7, 30)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(2022, 0, 19, 8, 30)
            timeInMillis
        }
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            .putExtra(CalendarContract.Events.TITLE, "Yoga")
            .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
            .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com")
        startActivity(intent)

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