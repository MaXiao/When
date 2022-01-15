package com.xiaoism.time.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xiaoism.time.ui.main.group.GroupListFragment
import com.xiaoism.time.ui.main.people.PeopleListFragment

private const val NUM_PAGES = 2;

class TabHolderAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PeopleListFragment()
            1 -> GroupListFragment()
            else -> Fragment()
        }
    }
}