package com.xiaoism.time.ui.main.group

import android.app.ActivityGroup
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoism.time.R
import com.xiaoism.time.databinding.ActivityGroupBinding
import com.xiaoism.time.model.GroupWithPersons

class GroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityGroupBinding>(this, R.layout.activity_group)
        binding.lifecycleOwner = this

        val recyclerView = binding.recyclerview
        val adapter = GroupAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val group: GroupWithPersons? = intent.getParcelableExtra("group") as? GroupWithPersons
        Log.d("grou view", group.toString())

        group?.let {
            binding.data = it
            adapter.setGroup(it)
        }
    }
}