package com.xiaoism.time.ui.city

import android.app.Activity
import android.content.Intent
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaoism.time.model.City
import com.xiaoism.time.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold(content = { Content(viewModel) })
        }
    }

    @Composable
    fun Content(viewModel: MainViewModel) {
        var value by remember { mutableStateOf("") }
        val cities by viewModel.cities.observeAsState(initial = emptyList())

        Column() {
            TextField(
                value = value,
                onValueChange = {
                    value = it
                    viewModel.searchCity(it)
                },
                label = { Text("Enter city") },
                maxLines = 1,
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth()
            )

            LazyColumn() {
                items(cities) {
                    Text(
                        text = "${it.name}, ${it.country}",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 12.dp)
                            .weight(1f)
                            .fillMaxWidth()
                            .clickable { onItemClick(it) }
                    )
                }
            }
        }
    }

    private fun onItemClick(city: City) {
        val intent = Intent()
        intent.putExtra(CityActivityContract.CITY, city)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}