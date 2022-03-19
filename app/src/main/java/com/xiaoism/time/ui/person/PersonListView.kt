package com.xiaoism.time.ui.person

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xiaoism.time.model.PersonWithCity


@Composable
fun PersonListView() {
    Scaffold(
        content = { PersonListContent() },
        floatingActionButton = { CreateBtn(LocalContext.current) })
}

@Composable
private fun PersonListContent() {
    val vm: PersonListViewModel = hiltViewModel()
    val context = LocalContext.current
    val persons by vm.persons.observeAsState(initial = emptyList())

    LazyColumn() {
        items(persons) { person ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .clickable { onItemClick(person, context) }
            ) {
                person.city?.let {
                    Text(
                        text = "${person.person.name}, ${it.name}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = it.getLocalTime(),
                        fontSize = 14.sp
                    )
                }

            }
        }
    }
}

@Composable
private fun CreateBtn(context: Context) {
    FloatingActionButton(
        modifier = Modifier.testTag("addButton"),
        onClick = { addPerson(context) }) {
        Icon(Icons.Filled.Add, "")
    }
}

private fun onItemClick(person: PersonWithCity, context: Context) {
    val intent = Intent(context, PersonActivity::class.java)
    intent.putExtra("person", person.person.personId)
    context.startActivity(intent)
}

private fun addPerson(context: Context) {
    val intent = Intent(context, AddPersonActivity::class.java)
    context.startActivity(intent)
}