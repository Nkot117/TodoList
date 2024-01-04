package com.nkot.todolist.ui.TaskAdd


import android.app.Dialog
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkot.todolist.BaseApplication
import com.nkot.todolist.databinding.FragmentTaskAddBinding
import com.nkot.todolist.ui.dialog.DatePickerFragment

class TaskAddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskAddViewModel by viewModels {
        TaskAddViewModelFactory((activity?.application as BaseApplication).database.taskDao())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentTaskAddBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)
        val parent = binding.root.parent as FrameLayout
        parent.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
        binding.addTaskDeadline.setOnClickListener {
            val datePickerDialog = DatePickerFragment.newInstance()
            datePickerDialog.show(this.childFragmentManager, DatePickerFragment.TAG)
        }
        binding.buttonAddTask.setOnClickListener {
            addTask()
            dialog.dismiss()
        }
        return dialog
    }

    private fun addTask() {
        val title = binding.addTaskTitle.text.toString()
        val description = binding.addTaskDescription.text.toString()
        val deadline = binding.addTaskDeadline.text.toString()
        viewModel.addTask(title, description, deadline.takeIf { it.isNotBlank() })
    }

    companion object {
        fun newInstance(): TaskAddFragment {
            return TaskAddFragment()
        }
    }

}
