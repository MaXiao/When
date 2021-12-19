package com.xiaoism.time.ui.main.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoism.time.R
import com.xiaoism.time.databinding.FragmentGroupsBinding
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.Person

class GroupListFragment : Fragment(), OnGroupClickListener {
    private lateinit var viewModel: GroupListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGroupsBinding>(
            inflater, R.layout.fragment_groups, container, false
        )
        binding.lifecycleOwner = this

        val recyclerView = binding.groupList
        val adapter = GroupListAdapter(requireContext(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(GroupListViewModel::class.java)
        viewModel.groups.observe(viewLifecycleOwner, Observer { groups ->
            groups?.let {
                adapter.setGroups(groups)
            }
        })

        val fab = binding.addGroup;
        fab.setOnClickListener {
            viewModel.addGroup()
        }

        return binding.root
    }

    override fun onItemClick(group: GroupWithPersons) {
        val intent = Intent(activity, GroupActivity::class.java)
        intent.putExtra("group", group)
        startActivity(intent)
    }
}