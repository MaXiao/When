package com.xiaoism.time.ui.main

import android.provider.Contacts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xiaoism.time.ui.main.group.GroupListFragment
import com.xiaoism.time.ui.main.people.AddPersonFragment
import com.xiaoism.time.ui.main.people.PeopleListFragment

private const val NUM_PAGES = 3;

class TabHolderAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AddPersonFragment()
            1 -> PeopleListFragment()
            else -> GroupListFragment()
        }
    }
}