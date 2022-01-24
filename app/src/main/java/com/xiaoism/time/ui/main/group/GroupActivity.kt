package com.xiaoism.time.ui.main.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

@AndroidEntryPoint
class GroupActivity : ComponentActivity() {
    private val viewModel by viewModels<GroupViewModel>()
    private val currentDate = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val (sliderTouched, setSliderTouched) = remember { mutableStateOf(false) }
        val (sliderPosition, setSliderPosition) = remember { mutableStateOf(0f) }
        val date = if (sliderTouched) convertTime(sliderPosition.roundToInt() * 5) else currentDate

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

            Slider(sliderPosition, setSliderPosition, setSliderTouched)
            Text(text = date.toString())
        }
    }

    @Composable
    private fun Slider(
        sliderPosition: Float,
        setSliderPosition: (Float) -> Unit,
        setSliderTouched: (Boolean) -> Unit
    ) {
        Slider(
            value = sliderPosition,
            onValueChange = {
                setSliderTouched(true)
                setSliderPosition(it)
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

    private fun convertTime(mins: Int): Date {
        val hour = mins / MIN_PER_HOUR
        val min = mins % MIN_PER_HOUR
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, min)
        return cal.time
    }

    private fun editGroup(groupId: Long) {
        val intent = Intent(this, CreateGroupActivity::class.java)
        intent.putExtra(CreateGroupActivity.GROUP_ID, groupId)
        startActivity(intent)
    }
    //endregion

    companion object {
        const val MIN_PER_HOUR = 60
        const val MIN_PER_DAY = MIN_PER_HOUR * 24
    }
}