package com.xiaoism.time.ui.main.city

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.xiaoism.time.R
import com.xiaoism.time.databinding.FragmentCityBinding
import com.xiaoism.time.model.City
import com.xiaoism.time.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityActivity : AppCompatActivity(), OnCityClickListener {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<FragmentCityBinding>(this, R.layout.fragment_city)
        binding.lifecycleOwner = this

        val recyclerView = binding.recyclerview
        val adapter = CityAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.cities.observe(this, Observer { cities ->
            cities?.let { adapter.setCities(cities) }
        })

        val input = findViewById<TextInputEditText>(R.id.input)
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                s.let {
                    viewModel.searchCity(it.toString())
                }
            }
        })
    }

    override fun onItemClick(city: City) {
        val intent = Intent()
        intent.putExtra(CityActivityContract.CITY, city)
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}