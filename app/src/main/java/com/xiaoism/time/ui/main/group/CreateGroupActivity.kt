package com.xiaoism.time.ui.main.group

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoism.time.R
import com.xiaoism.time.databinding.ActivityCreateGroupBinding
import com.xiaoism.time.model.GroupWithPersons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGroupActivity: AppCompatActivity() {
    private val viewModel by viewModels<CreateGroupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCreateGroupBinding>(this, R.layout.activity_create_group)
        binding.lifecycleOwner = this

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        val group: GroupWithPersons? = intent.getParcelableExtra("group") as? GroupWithPersons

        val saveBtn = binding.save
        saveBtn.setOnClickListener {

        }

        val selectBtn = binding.addMore
        selectBtn.setOnClickListener {

        }

    }

}