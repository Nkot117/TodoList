package com.nkot.todolist.ui.TaskList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nkot.todolist.BaseApplication
import com.nkot.todolist.adapter.TaskListAdapter


import com.nkot.todolist.databinding.FragmentTaskListBinding
import com.nkot.todolist.ui.TaskAdd.TaskAddFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskListViewModel by viewModels {
        TaskListViewModelFactory((activity?.application as BaseApplication).database.taskDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(layoutInflater)
        val taskListAdapter = TaskListAdapter(
            onItemClicked = {
                val action =
                    TaskListFragmentDirections.actionTaskListFragmentToTaskEditFragment(
                        "${it.title}の編集",
                        it.id
                    )
                this.findNavController().navigate(action)
            },
            onItemCompleteButtonClicked = {
                viewModel.changeTaskStatus(it)
            }
        )

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.allTasks.collect {
                taskListAdapter.submitList(it)
            }
        }

        val itemTouchHelper = getItemTouchHelperForSwipeToDelete(taskListAdapter)

        binding.taskListRecyclerView.also {
            itemTouchHelper.attachToRecyclerView(it)
            it.adapter = taskListAdapter
            it.layoutManager = LinearLayoutManager(this.context)
            it.addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )

        }

        binding.addTaskFab.setOnClickListener {
            TaskAddFragment.newInstance().show(childFragmentManager, "TaskAddDialog")
        }

        return binding.root
    }

    private fun getItemTouchHelperForSwipeToDelete(taskListAdapter: TaskListAdapter): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                target: androidx.recyclerview.widget.RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                direction: Int
            ) {
                context?.let {
                    MaterialAlertDialogBuilder(it)
                        .setMessage("タスクを削除しますか？")
                        .setNegativeButton("削除する") { _, _ ->
                            val position = viewHolder.adapterPosition
                            val task = taskListAdapter.currentList[position]
                            viewModel.deleteTask(task)
                        }
                        .setPositiveButton("キャンセル") { _, _ ->
                            taskListAdapter.notifyItemChanged(viewHolder.adapterPosition)
                        }
                        .show()
                }
            }
        })
    }
}
