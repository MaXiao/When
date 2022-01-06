package com.xiaoism.time.ui.main.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.xiaoism.time.model.Group
import com.xiaoism.time.model.GroupWithPersons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupListFragment : Fragment(), OnGroupClickListener {
    private val viewModel by viewModels<GroupListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding = DataBindingUtil.inflate<FragmentGroupsBinding>(
//            inflater, R.layout.fragment_groups, container, false
//        )
//        binding.lifecycleOwner = this
//
//        val recyclerView = binding.groupList
//        val adapter = GroupListAdapter(requireContext(), this)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        viewModel = ViewModelProvider(this).get(GroupListViewModel::class.java)
//        viewModel.groups.observe(viewLifecycleOwner, Observer { groups ->
//            groups?.let {
//                adapter.setGroups(groups)
//            }
//        })
//
//        val fab = binding.addGroup;
//        fab.setOnClickListener {
//            createGroup()
//        }
//
//        return binding.root

        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(floatingActionButton = { createBtn() }, content = { listContent() })
            }
        }
    }

    @Composable
    private fun listContent() {
        val groups by viewModel.groups.observeAsState(initial = emptyList())


        LazyColumn {
            items(groups) { group ->
                groupRow(group = group)
            }
        }


    }

    @Composable
    private fun createBtn() {
        val onClick = {
            createGroup()
        }
        FloatingActionButton(onClick = onClick) {
            Icon(Icons.Filled.Add, "")
        }
    }

    @Composable
    private fun groupRow(group: GroupWithPersons) {
        Row {
            Text(text = "${group.group.name}")
        }
    }

    override fun onItemClick(group: GroupWithPersons) {
        val intent = Intent(activity, GroupActivity::class.java)
        intent.putExtra("group", group)
        startActivity(intent)
    }

    private fun createGroup() {
        val intent = Intent(activity, CreateGroupActivity::class.java)
        startActivity(intent)
    }
}