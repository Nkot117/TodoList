package com.nkot.todolist.adapter


import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nkot.todolist.R
import com.nkot.todolist.database.Task.TaskEntity
import com.nkot.todolist.database.Task.deadlineToString
import com.nkot.todolist.databinding.TaskRowItemBinding

class TaskListAdapter(
    private val onItemClicked: (TaskEntity) -> Unit,
    private val onItemCompleteButtonClicked: (TaskEntity) -> Unit
) :
    ListAdapter<TaskEntity, TaskListAdapter.TaskViewHolder>(DiffCallback) {

    class TaskViewHolder(private val binding: TaskRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            task: TaskEntity, onItemClicked: (TaskEntity) -> Unit,
            onItemCompleteButtonClicked: (TaskEntity) -> Unit
        ) {
            binding.taskTitle.text = task.title
            binding.taskDeadline.text = task.deadlineToString()
            if (task.completed) {
                binding.taskCompleteButton.setImageResource(R.drawable.icon_checked_status_button)
                binding.taskTitle.paintFlags = binding.taskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
                binding.taskTitle.alpha = 0.5f
            } else {
                binding.taskCompleteButton.setImageResource(R.drawable.icon_unchecked_status_button)
                binding.taskTitle.paintFlags =
                    binding.taskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
                binding.taskTitle.alpha = 1.0f
            }
            binding.taskCompleteButton.setOnClickListener {
                onItemCompleteButtonClicked(task)
            }
            binding.taskRowItem.setOnClickListener {
                onItemClicked(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = TaskRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, onItemClicked, onItemCompleteButtonClicked)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TaskEntity>() {
            override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
