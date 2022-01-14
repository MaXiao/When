package com.xiaoism.time.ui.main.people

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.textfield.TextInputEditText

class AddPersonActivity : ComponentActivity() {
    private val viewModel by viewModels<AddPersonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            Scaffold (
                content = { content() }
            )
        }

    }

    @Composable
    private fun content() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            var name by remember { mutableStateOf("name") }
            val city by viewModel.cityName.observeAsState(initial = "")

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text("Name") }
            )
            Divider(Modifier.height(10.dp))
            Row() {
                Text(city, fontSize = 16.sp)
                OutlinedButton(onClick = { viewModel.chooseCity() }) {
                    Text("Choose")
                }
            }

            Divider(Modifier.height(20.dp))
            OutlinedButton(onClick = { viewModel.confirm(name) }) {
                Text("Save")
            }

        }
    }
}