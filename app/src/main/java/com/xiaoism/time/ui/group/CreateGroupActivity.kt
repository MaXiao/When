package com.xiaoism.time.ui.group

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.ui.people.PersonsSelectActivityContract
import com.xiaoism.time.util.livedata.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGroupActivity : AppCompatActivity() {
    private val viewModel by viewModels<CreateGroupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val groupId = intent.getLongExtra(GROUP_ID, -1)
        if (groupId >= 0) {
            viewModel.configGroup(groupId)
        }

        viewModel.destination.observe(this, EventObserver {
            when (it) {
                CreateGroupViewModel.Destination.PERSON_LIST -> goToSelectMembers()
                CreateGroupViewModel.Destination.UPDATE_DONE -> {
                    finish()
                }
            }
        })

        setContent {
            Scaffold(content = { Content(viewModel) }, floatingActionButton = { AddButton() })
        }
    }

    @Composable
    private fun Content(viewModel: CreateGroupViewModel) {
        val persons by viewModel.persons.observeAsState(initial = emptyList())
        val name by viewModel.name.observeAsState(initial = "")
        val group = viewModel.group?.observeAsState()?.value

        LaunchedEffect(group) {
            viewModel.updateData(group)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = name,
                onValueChange = {
                    viewModel.updateName(it)
                },
                label = { Text("Enter name") },
                maxLines = 1,
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
            )
            LazyColumn() {
                items(persons) { person ->
                    Text(
                        text = person.person.name,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 12.dp)
                            .fillMaxWidth()
                            .clickable { onItemClick(person) }
                    )
                }
            }
            OutlinedButton(onClick = { viewModel.save() }) {
                Text(text = "Save")
            }
        }
    }

    @Composable
    private fun AddButton() {
        FloatingActionButton(onClick = { goToSelectMembers() }) {
            Icon(Icons.Filled.Add, "add member")
        }
    }

    private val selectMembers = registerForActivityResult(PersonsSelectActivityContract()) { list ->
        list?.let {
            viewModel.updateMembers(it)
        }
    }

    private fun goToSelectMembers() {
        selectMembers.launch(PersonsSelectActivityContract.PersonSelectInput(multiSelect = true))
    }

    private fun onItemClick(person: PersonWithCity) {
        viewModel.removeMember(person)
    }

    companion object {
        const val GROUP_ID = "GROUP_ID"
    }
}