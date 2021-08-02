package com.xiaoism.time.ui.main.people

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiaoism.time.databinding.CellPeopleBinding
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity

class PeopleListAdapter(context: Context) :
    RecyclerView.Adapter<PeopleListAdapter.PeopleViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var people = emptyList<PersonWithCity>()

    inner class PeopleViewHolder(val binding: CellPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: PersonWithCity) {
            binding.item = p
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding = CellPeopleBinding.inflate(inflater, parent, false)
        return PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(people[position])
    }

    override fun getItemCount(): Int = people.size

    fun setPeople(people: List<PersonWithCity>) {
        this.people = people
        notifyDataSetChanged()
    }
}