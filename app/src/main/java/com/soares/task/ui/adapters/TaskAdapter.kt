package com.soares.task.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soares.task.R
import com.soares.task.databinding.ItemTaskBinding
import com.soares.task.domain.models.Task


class TaskAdapter(
    private val items: MutableList<Task>,
    private val onItemMenuClickListener: OnItemTaskClickListener,
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private lateinit var priorities: Array<String>

    interface OnItemTaskClickListener {
        fun onEdit(task: Task)
        fun onRemove(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(layoutInflater)

        priorities = parent.context.resources.getStringArray(R.array.array_priority)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]

            val task: Task = item
            binding.task = task

            binding.textPriority.text = priorities[task.priorityId]

            binding.imageEdit.setOnClickListener {
                onItemMenuClickListener.onEdit(items[adapterPosition])
            }

            binding.imageRemove.setOnClickListener{
                onItemMenuClickListener.onRemove(items[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}