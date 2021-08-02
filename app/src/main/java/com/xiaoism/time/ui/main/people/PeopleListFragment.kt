package com.xiaoism.time.ui.main.people

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoism.time.R
import com.xiaoism.time.databinding.FragmentPersonBinding
import com.xiaoism.time.model.City

class PeopleListFragment : Fragment() {
    private lateinit var viewModel: PeopleListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPersonBinding>(
            inflater,
            R.layout.fragment_person,
            container,
            false
        )
        binding.lifecycleOwner = this

        val recyclerView = binding.recyclerview
        val adapter = PeopleListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(PeopleListViewModel::class.java)
        viewModel.people.observe(viewLifecycleOwner, Observer { people ->
            people?.let {
                adapter.setPeople(people)
            }
        })

        return binding.root
    }
}