package com.nkot.todolist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nkot.todolist.adapter.TaskListAdapter


import com.nkot.todolist.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

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

        return binding.root
    }

}