package com.xiaoism.time.ui.main.group

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiaoism.time.databinding.CellGroupBinding
import com.xiaoism.time.model.City
import com.xiaoism.time.model.GroupWithPersons

interface OnGroupClickListener {
    fun onItemClick(group: GroupWithPersons)
}

class GroupListAdapter(context: Context, val listener: OnGroupClickListener) :
    RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var groups = emptyList<GroupWithPersons>()

    inner class ViewHolder(val binding: CellGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(g: GroupWithPersons, listener: OnGroupClickListener) {
            binding.item = g
            binding.textview.setOnClickListener {
                listener.onItemClick(g)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CellGroupBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(groups[position], listener)
    }

    override fun getItemCount(): Int = groups.size

    fun setGroups(groups: List<GroupWithPersons>) {
        this.groups = groups
        notifyDataSetChanged()
    }
}