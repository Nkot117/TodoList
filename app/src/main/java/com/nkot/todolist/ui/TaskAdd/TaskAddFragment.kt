package com.nkot.todolist.ui.TaskAdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nkot.todolist.BaseApplication
import com.nkot.todolist.databinding.FragmentTaskAddBinding

class TaskAddFragment : Fragment() {
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskAddViewModel by viewModels {
        TaskAddViewModelFactory((activity?.application as BaseApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskAddBinding.inflate(layoutInflater)
        binding.buttonAddTask.setOnClickListener {
            addTask()
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun addTask() {
        val title = binding.editTaskTitle.text.toString()
        val description = binding.editTaskDescription.text.toString()
        viewModel.addTask(title, description)
    }
}