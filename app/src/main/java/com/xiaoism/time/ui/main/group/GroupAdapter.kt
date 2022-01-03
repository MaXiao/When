package com.xiaoism.time.ui.main.group

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiaoism.time.databinding.CellPeopleBinding
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.ui.main.people.PersonCellState

class GroupAdapter(context: Context) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private lateinit var group: GroupWithPersons

    inner class GroupViewHolder(val binding: CellPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: PersonWithCity) {
            binding.item = PersonCellState(person, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = CellPeopleBinding.inflate(inflater, parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(group.persons[position])
    }

    override fun getItemCount(): Int = group.persons.size

    fun setGroup(group: GroupWithPersons) {
        this.group = group
        notifyDataSetChanged()
    }
}