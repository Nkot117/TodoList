package com.nkot.todolist.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkot.todolist.database.Task.TaskEntity
import com.nkot.todolist.databinding.TaskRowItemBinding

class TaskListAdapter(private val onItemClicked: (TaskEntity) -> Unit) :
    ListAdapter<TaskEntity, TaskListAdapter.TaskViewHolder>(DiffCallback) {

        class TaskViewHolder(private val binding: TaskRowItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(task: TaskEntity) {
                    binding.taskTitle.text = task.title
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = TaskRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(task)
        }
        holder.bind(task)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TaskEntity>() {
            override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}