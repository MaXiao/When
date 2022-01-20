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
    companion object {
        const val MULTI_CHOICE = "MULTI_CHOICE"
        const val EXISTING_MEMBER = "EXISTING_MEMBER"
    }

    private val viewModel by viewModels<PersonSelectionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // default to be multi-choice
        viewModel.multiChoice = arguments?.getBoolean(MULTI_CHOICE) ?: true
        val existingMembers: ArrayList<PersonWithCity> =
            arguments?.getParcelableArrayList(EXISTING_MEMBER) ?: ArrayList()
        val memberIds = existingMembers.map { member -> member.person.personId }

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

        viewModel.people.observe(viewLifecycleOwner, Observer { persons ->
            persons?.let {
                val filtered = persons.filter { p -> !memberIds.contains(p.person.personId) }
                adapter.setPeople(filtered)
            }
        })

        viewModel.selection.observe(viewLifecycleOwner, { selection ->
            selection?.let {
                adapter.setSelection(selection)
            }
        })


        binding.save.visibility = if (viewModel.multiChoice) View.VISIBLE else View.GONE
        binding.save.setOnClickListener {
            confirmSelection()
        }

        return binding.root
    }

    override fun onItemClick(person: PersonWithCity, index: Int) {
        viewModel.toggleSelection(person)
        if (!viewModel.multiChoice) {
            confirmSelection()
        }
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