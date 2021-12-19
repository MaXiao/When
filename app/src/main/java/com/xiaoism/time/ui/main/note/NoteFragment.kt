package com.xiaoism.time.ui.main.note

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
import com.xiaoism.time.databinding.FragmentNotesBinding
import com.xiaoism.time.model.Note
import com.xiaoism.time.ui.main.people.PeopleListAdapter
import com.xiaoism.time.ui.main.people.PeopleListViewModel

class NoteFragment : Fragment() {
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNotesBinding>(
            inflater, R.layout.fragment_notes, container, false
        )
        binding.lifecycleOwner = this

        val recyclerView = binding.recyclerView
        val adapter = NoteAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer { list ->
            Log.e("note list", "note list updated" + list.toString())
            list?.let { adapter.setNotes(it) }
        })

        val fab = binding.add;
        fab.setOnClickListener {
            viewModel.create(Note("title1"))
        }

        return binding.root
    }

}