package com.xiaoism.time.ui.main.people

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.xiaoism.time.R
import com.xiaoism.time.databinding.FragmentAddPersonBinding
import com.xiaoism.time.model.City
import com.xiaoism.time.ui.main.city.CityActivityContract
import com.xiaoism.time.util.livedata.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPersonFragment : Fragment() {
    private val viewModel by viewModels<AddPersonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddPersonBinding>(
            inflater,
            R.layout.fragment_add_person,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.destination.observe(requireActivity(), EventObserver {
            when (it) {
                AddPersonViewModel.Destination.CITY -> goToAddCity()
            }
        })

        return binding.root
    }

    private val getCity =
        registerForActivityResult(CityActivityContract()) { city: City? ->
            viewModel.updateCity(city)
        }

    private fun goToAddCity() {
        getCity.launch()
    }
}