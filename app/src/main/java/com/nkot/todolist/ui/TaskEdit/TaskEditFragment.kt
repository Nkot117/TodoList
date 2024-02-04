package com.nkot.todolist.ui.TaskEdit

import android.os.Bundle
import android.text.TextWatcher
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
import com.nkot.todolist.database.Task.deadlineToString
import com.nkot.todolist.databinding.FragmentTaskEditBinding
import com.nkot.todolist.ui.dialog.DatePickerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskEditFragment : Fragment() {
    private var _binding: FragmentTaskEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskEditViewModel by viewModels {
        TaskEditViewModelFactory((activity?.application as BaseApplication).database.taskDao())
    }

    private val navigationArgs: TaskEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskEditBinding.inflate(layoutInflater)
        val id = navigationArgs.id
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getTask(id).collect { taskEntity ->
                bindTask(taskEntity)
            }
        }

        binding.editTaskDeadline.setOnClickListener {
            val datePickerDialog = DatePickerFragment.newInstance()
            datePickerDialog.show(this.childFragmentManager, DatePickerFragment.TAG)
        }

        binding.editTaskTitle.addTextChangedListener(object: TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buttonAddTask.isEnabled = s?.isNotBlank() == true
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // NOP
            }
            override fun afterTextChanged(s: android.text.Editable?) {
                // NOP
            }
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindTask(taskEntity: TaskEntity) {
        viewModel.editTask = taskEntity
        binding.editTaskTitle.setText(taskEntity.title)
        binding.editTaskDescription.setText(taskEntity.description)
        binding.editTaskDeadline.setText(taskEntity.deadlineToString())
        binding.buttonAddTask.setOnClickListener {
            updateTask()
            findNavController().navigateUp()
        }
    }

    private fun updateTask() {
        val title = binding.editTaskTitle.text.toString()
        val description = binding.editTaskDescription.text.toString()
        val deadline = binding.editTaskDeadline.text.toString().takeIf { it.isNotBlank() }
        viewModel.updateTask(title, description, deadline)
    }
}
