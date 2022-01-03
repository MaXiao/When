package com.xiaoism.time.ui.main.group

import android.os.Bundle
import android.util.Log
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoism.time.R
import com.xiaoism.time.databinding.ActivityCreateGroupBinding
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.ui.main.people.OnPersonClickListener
import com.xiaoism.time.ui.main.people.PeopleListAdapter
import com.xiaoism.time.ui.main.people.PersonsSelectActivityContract
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

        val group: GroupWithPersons? = intent.getParcelableExtra("group") as? GroupWithPersons


        val selectBtn = binding.addMore
        selectBtn.setOnClickListener {
            viewModel.save()
        }

        viewModel.destination.observe(this, EventObserver {
            when (it) {
                CreateGroupViewModel.Destination.PERSON_LIST -> goToSelectMembers()
            }
        })

        viewModel.persons.observe(this, Observer { list ->
            adapter.setPeople(list)
        })

    }

    private val selectMembers = registerForActivityResult(PersonsSelectActivityContract()) { list ->
        Log.e("group", list?.toString())
        list?.let {
            viewModel.updateMembers(it)
        }
    }

    private fun goToSelectMembers() {
        Log.e("group", "go select person")
        selectMembers.launch()
    }

    override fun onItemClick(person: PersonWithCity, index: Int) {

    }
}