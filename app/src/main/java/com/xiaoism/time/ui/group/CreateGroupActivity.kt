package com.xiaoism.time.ui.group

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoism.time.R
import com.xiaoism.time.databinding.ActivityCreateGroupBinding
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.ui.people.OnPersonClickListener
import com.xiaoism.time.ui.people.PeopleListAdapter
import com.xiaoism.time.ui.people.PersonsSelectActivityContract
import com.xiaoism.time.util.livedata.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGroupActivity : AppCompatActivity(), OnPersonClickListener {
    private val viewModel by viewModels<CreateGroupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCreateGroupBinding>(
            this,
            R.layout.activity_create_group
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val recyclerView = binding.recyclerview
        val adapter = PeopleListAdapter(this, this)
        recyclerView.adapter = adapter;
        recyclerView.layoutManager = LinearLayoutManager(this)

        val groupId = intent.getLongExtra(GROUP_ID, -1)
        if (groupId >= 0) {
            viewModel.configGroup(groupId)

            val input = binding.input
            viewModel.group?.observe(this, { group ->
                adapter.setPeople(group.persons)
                input.setText(group.group.name)
            })
        }

        val selectBtn = binding.addMore
        selectBtn.setOnClickListener {
            viewModel.save()
        }

        viewModel.persons.observe(this, Observer { list ->
            adapter.setPeople(list)
        })

        viewModel.destination.observe(this, EventObserver {
            when (it) {
                CreateGroupViewModel.Destination.PERSON_LIST -> goToSelectMembers()
                CreateGroupViewModel.Destination.UPDATE_DONE -> {
                    finish()
                }
            }
        })
    }

    private val selectMembers = registerForActivityResult(PersonsSelectActivityContract()) { list ->
        list?.let {
            viewModel.updateMembers(it)
        }
    }

    private fun goToSelectMembers() {
        selectMembers.launch(PersonsSelectActivityContract.PersonSelectInput(multiSelect = true))
    }

    override fun onItemClick(person: PersonWithCity, index: Int) {
        viewModel.removeMember(person)
    }

    companion object {
        const val GROUP_ID = "GROUP_ID"
    }
}