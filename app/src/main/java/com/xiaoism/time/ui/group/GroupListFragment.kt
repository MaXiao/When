package com.xiaoism.time.ui.group

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.xiaoism.time.model.GroupWithPersons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupListFragment : Fragment() {
    private val viewModel by viewModels<GroupListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                GroupListContent()
            }
        }
    }

    @Composable
    fun GroupListContent() {
        Scaffold(floatingActionButton = { createBtn() }, content = { listContent() })
    }

    @Composable
    private fun listContent() {
        val groups by viewModel.groups.observeAsState(initial = emptyList())

        LazyColumn {
            items(groups) { group ->
                groupRow(group = group)
                Divider(color = Color.Gray, thickness = 6.dp)
            }
        }
    }

    @Composable
    private fun createBtn() {
        val onClick = {
            createGroup()
        }
        FloatingActionButton(modifier = Modifier.testTag("addButton"), onClick = onClick) {
            Icon(Icons.Filled.Add, "")
        }
    }

    @Composable
    private fun groupRow(group: GroupWithPersons) {
        Row(
            modifier = Modifier
                .testTag("groupRow")
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .clickable { onItemClick(group) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${group.group.name}",
                fontSize = 20.sp
            )
            Text(text = "${group.persons.size}")
        }
    }

    private fun onItemClick(group: GroupWithPersons) {
        val intent = Intent(activity, GroupActivity::class.java)
        intent.putExtra("group", group.group.groupId)
        startActivity(intent)
    }

    private fun createGroup() {
        val intent = Intent(activity, CreateGroupActivity::class.java)
        startActivity(intent)
    }
}