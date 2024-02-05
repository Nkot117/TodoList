package com.nkot.todolist.ui.TaskAdd


import android.app.Dialog
import android.os.Bundle
import android.text.TextWatcher
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkot.todolist.databinding.FragmentTaskAddBinding
import com.nkot.todolist.ui.dialog.DatePickerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskAddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskAddViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentTaskAddBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)
        val parent = binding.root.parent as FrameLayout
        parent.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
        binding.addTaskTitle.addTextChangedListener(object : TextWatcher {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun addTask() {
        val title = binding.addTaskTitle.text.toString()
        val description = binding.addTaskDescription.text.toString()
        val deadline = binding.addTaskDeadline.text.toString().takeIf { it.isNotBlank() }
        viewModel.addTask(title, description, deadline)
    }

    companion object {
        fun newInstance(): TaskAddFragment {
            return TaskAddFragment()
        }
    }

}
