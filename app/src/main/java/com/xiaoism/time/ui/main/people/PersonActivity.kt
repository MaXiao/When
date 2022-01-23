package com.xiaoism.time.ui.main.people

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaoism.time.model.City
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.ui.main.city.CityActivityContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonActivity : ComponentActivity() {
    private val viewModel by viewModels<PersonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val personId = intent.getLongExtra("person", -1)
        if (personId > 0) {
            viewModel.configPerson(personId)
        }

        setContent {
            Scaffold(content = { Content() })
        }
    }

    //region views
    @Composable
    private fun Content() {
        val person by viewModel.person.observeAsState(
            initial = PersonWithCity(
                person = Person(
                    name = "",
                    cityId = ""
                ), city = null
            )
        )
        var editingName by remember { mutableStateOf(false)}
        var name by remember { mutableStateOf(person.person.name)}

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (editingName) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = { Text(person.person.name) }
                    )
                } else {
                    Text(
                        person.person.name,
                        fontSize = 30.sp
                    )
                }

                if (editingName) {
                    OutlinedButton(onClick = {
                        editingName = !editingName
                        viewModel.updateName(name)
                    }) {
                        Text("Save")
                    }
                } else {
                    OutlinedButton(onClick = { editingName = !editingName }) {
                        Text("Change")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            person.city?.let { city ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "${city.name}, ${city.country}",
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            city.getLocalTime() ?: "",
                            fontSize = 20.sp
                        )
                    }
                    OutlinedButton(onClick = { goToAddCity() }) {
                        Text("Change")
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(onClick = { delete() }) {
                Text("Delete")
            }
        }
    }
    //endregion

    //region functions
    private val getCity =
        registerForActivityResult(CityActivityContract()) { city: City? ->
            viewModel.updateCity(city)
        }

    private fun goToAddCity() {
        getCity.launch()
    }

    private fun delete() {
        finish()
        viewModel.delete()
    }
    //endregion
}