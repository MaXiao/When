package com.xiaoism.time.ui.main.group

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.PersonWithCity

class GroupActivity : ComponentActivity() {

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
                    row(person)
                    Divider(color = Color.Gray, thickness = 6.dp)
                }
            }
        }
    }

    @Composable
    private fun row(person: PersonWithCity) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(person.person.name, fontSize = 16.sp)
            Text(person.city!!.name, fontSize = 13.sp, color = Color.LightGray)
        }
    }
}