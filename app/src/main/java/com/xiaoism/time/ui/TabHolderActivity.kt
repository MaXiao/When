package com.xiaoism.time.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xiaoism.time.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabHolderActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        viewPager = findViewById(R.id.pager)
        viewPager.adapter = TabHolderAdapter(this)

        val tabLayout = findViewById<TabLayout>(R.id.tab_main)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Groups"
                1 -> tab.text = "Person List"
                else -> {
                }
            }
        }.attach()
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}