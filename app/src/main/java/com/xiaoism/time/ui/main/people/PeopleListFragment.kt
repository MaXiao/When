package com.xiaoism.time.ui.main.people

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
import com.xiaoism.time.databinding.FragmentPersonBinding
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleListFragment : Fragment(), OnPersonClickListener {
    private val viewModel by viewModels<PeopleListViewModel>()

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
        val adapter = PeopleListAdapter(requireContext(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.people.observe(viewLifecycleOwner, Observer { people ->
            people?.let {
                adapter.setPeople(people)
            }
        })

        val fab = binding.fab;
        fab.setOnClickListener {
            addPerson()
        }

        return binding.root
    }

    override fun onItemClick(person: PersonWithCity, index: Int) {
        val intent = Intent(requireContext(), PersonActivity::class.java)
        intent.putExtra("person", person.person.personId)
        startActivity(intent)
    }

    private fun addPerson() {
        val intent = Intent(requireActivity(), AddPersonActivity::class.java)
        startActivity(intent)
    }
}