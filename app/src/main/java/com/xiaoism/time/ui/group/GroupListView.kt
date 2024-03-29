package com.xiaoism.time.ui.group

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xiaoism.time.model.GroupWithPersons

@Composable
fun GroupListView() {
    Scaffold(
        modifier = Modifier.testTag("GroupListView"),
        floatingActionButton = { CreateBtn() },
        content = { ListContent() })
}

@Composable
private fun ListContent() {
    val viewModel: GroupListViewModel = hiltViewModel()
    val groups by viewModel.groups.observeAsState(initial = emptyList())

    LazyColumn {
        items(groups) { group ->
            GroupRow(group = group)
            Divider(color = Color.Gray, thickness = 6.dp)
        }
    }
}

@Composable
private fun CreateBtn() {
    val context = LocalContext.current
    FloatingActionButton(
        modifier = Modifier.testTag("addButton"),
        onClick = { createGroup(context) }) {
        Icon(Icons.Filled.Add, "")
    }
}

@Composable
private fun GroupRow(group: GroupWithPersons) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .testTag("groupRow")
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .clickable { onItemClick(group, context) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = group.group.name,
            fontSize = 20.sp
        )
        Text(text = "${group.persons.size}")
    }
}

private fun onItemClick(group: GroupWithPersons, context: Context) {
    val intent = Intent(context, GroupActivity::class.java)
    intent.putExtra("group", group.group.groupId)
    context.startActivity(intent)
}

private fun createGroup(context: Context) {
    val intent = Intent(context, CreateGroupActivity::class.java)
    context.startActivity(intent)
}