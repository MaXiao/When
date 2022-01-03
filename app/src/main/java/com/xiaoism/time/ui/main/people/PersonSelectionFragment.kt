package com.xiaoism.time.ui.main.people

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoism.time.R
import com.xiaoism.time.databinding.FragmentPersonSelectionBinding
import com.xiaoism.time.model.PersonWithCity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonSelectionFragment : Fragment(), OnPersonClickListener {
    private val viewModel by viewModels<PersonSelectionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPersonSelectionBinding>(
            inflater,
            R.layout.fragment_person_selection,
            container,
            false
        )
        binding.lifecycleOwner = this

        val recyclerView = binding.recyclerview
        val adapter = PeopleListAdapter(requireContext(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.people.observe(viewLifecycleOwner, Observer { people ->
            people?.let {
                adapter.setPeople(people)
            }
        })

        viewModel.selection.observe(viewLifecycleOwner, { selection ->
            selection?.let {
                adapter.setSelection(selection)
            }
        })

        binding.save.setOnClickListener {
            confirmSelection()
        }

        return binding.root
    }

    override fun onItemClick(person: PersonWithCity, index: Int) {
        viewModel.toggleSelection(person)
    }

    private fun confirmSelection() {
        viewModel.selection.value?.let { selection ->
            val intent = Intent()
            intent.putParcelableArrayListExtra(
                PersonsSelectActivityContract.PERSON_LIST,
                ArrayList(selection)
            )
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }

    }
}