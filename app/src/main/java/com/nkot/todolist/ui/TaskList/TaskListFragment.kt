package com.nkot.todolist.ui.TaskList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
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
        val taskListAdapter = TaskListAdapter {
            Log.d("TaskListFragment", "Clicked on task item")
        }
        recyclerView.adapter = taskListAdapter

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.allTasks.collect {
                taskListAdapter.submitList(it)
            }
        }

        binding.addTaskFab.setOnClickListener {
            val action =  TaskListFragmentDirections.actionTaskListFragmentToTaskAddFragment()
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }
}