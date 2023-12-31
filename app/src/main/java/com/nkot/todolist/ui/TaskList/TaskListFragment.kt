package com.nkot.todolist.ui.TaskList

import android.os.Bundle
import android.util.Log
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
import com.nkot.todolist.BaseApplication
import com.nkot.todolist.adapter.TaskListAdapter


import com.nkot.todolist.databinding.FragmentTaskListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskListViewModel by viewModels {
        TaskListViewModelFactory((activity?.application as BaseApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(layoutInflater)
        val recyclerView = binding.taskListRecyclerView
        val taskListAdapter = TaskListAdapter(
            onItemClicked = {
                val action =
                    TaskListFragmentDirections.actionTaskListFragmentToTaskAddFragment("${it.title}の編集", it.id)
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

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
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
                val position = viewHolder.adapterPosition
                val task = taskListAdapter.currentList[position]
                viewModel.deleteTask(task)
            }
        })

        recyclerView.adapter = taskListAdapter
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.addTaskFab.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListFragmentToTaskAddFragment(getString(com.nkot.todolist.R.string.add_task_fragment_title))
            this.findNavController().navigate(action)
        }

        return binding.root
    }
}
