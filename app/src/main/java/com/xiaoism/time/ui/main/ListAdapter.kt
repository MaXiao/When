package com.xiaoism.time.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.xiaoism.time.R
import com.xiaoism.time.databinding.ListCellItemBinding
import kotlinx.android.synthetic.main.list_cell_item.view.*

class ListAdapter(context: Context) : RecyclerView.Adapter<ListAdapter.TimeViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var times = emptyList<TimeEntity>()

    inner class TimeViewHolder(val binding: ListCellItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(time: TimeEntity) {
            binding.item = time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding = ListCellItemBinding.inflate(inflater, parent, false);
        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(times[position])
    }

    fun setTimes(times: List<TimeEntity>) {
        this.times = times
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = times.size
}