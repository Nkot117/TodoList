package com.nkot.todolist.ui.TaskEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nkot.todolist.BaseApplication
import com.nkot.todolist.database.Task.TaskEntity
import com.nkot.todolist.databinding.FragmentTaskEditBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskEditFragment : Fragment() {
    private var _binding: FragmentTaskEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskAddViewModel by viewModels {
        TaskEditViewModelFactory((activity?.application as BaseApplication).database.taskDao())
    }

    private val navigationArgs: TaskEditFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskEditBinding.inflate(layoutInflater)
        val id = navigationArgs.id
        if (id > 0) {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.getTask(id).collect { TaskEntity ->
                    binding.editTaskTitle.setText(TaskEntity.title)
                    binding.editTaskDescription.setText(TaskEntity.description)
                    binding.buttonAddTask.setOnClickListener {
                        updateTask(TaskEntity)
                        findNavController().navigateUp()
                    }
                }
            }

        } else {
            binding.buttonAddTask.setOnClickListener {
                addTask()
                findNavController().navigateUp()
            }
        }
        return binding.root
    }

    private fun addTask() {
        val title = binding.editTaskTitle.text.toString()
        val description = binding.editTaskDescription.text.toString()
        viewModel.addTask(title, description)
    }

    private fun updateTask(taskEntity: TaskEntity) {
        val updatedTask = TaskEntity(
            taskEntity.id,
            binding.editTaskTitle.text.toString(),
            binding.editTaskDescription.text.toString(),
            taskEntity.completed,
            taskEntity.created
        )

        viewModel.updateTask(updatedTask)
    }
}
