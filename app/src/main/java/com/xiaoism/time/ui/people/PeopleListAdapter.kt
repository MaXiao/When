package com.xiaoism.time.ui.people

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiaoism.time.databinding.CellPeopleBinding
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import java.util.*

interface OnPersonClickListener {
    fun onItemClick(person: PersonWithCity, index: Int);
}

class PeopleListAdapter(context: Context, val listener: OnPersonClickListener) :
    RecyclerView.Adapter<PeopleListAdapter.PeopleViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var people = emptyList<PersonWithCity>()
    private var selection = emptyList<Person>()

    inner class PeopleViewHolder(val binding: CellPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: PersonWithCity, index: Int) {
            binding.item = p
            binding.selected = selection.contains(p.person)
            binding.textview.setOnClickListener { listener.onItemClick(p, index) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding = CellPeopleBinding.inflate(inflater, parent, false)
        return PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(people[position], position)
    }

    override fun getItemCount(): Int = people.size

    fun setPeople(people: List<PersonWithCity>) {
        this.people = people.sortedBy { it.person.name.lowercase(Locale.getDefault()) }
        notifyDataSetChanged()
    }

    fun setSelection(selection: List<PersonWithCity>) {
        this.selection = selection.map { it -> it.person }
        notifyDataSetChanged()
    }
}