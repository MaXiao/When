package com.xiaoism.time.ui.main.people

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
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

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    person.person.name,
                    fontSize = 30.sp
                )
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text("Change")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            person.city?.let { city ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column() {
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
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Text("Change")
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    //endregion
}