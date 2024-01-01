package com.nkot.todolist.ui.TaskAdd


import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkot.todolist.databinding.FragmentTaskAddBinding
import com.nkot.todolist.ui.dialog.DatePickerFragment

class TaskAddFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentTaskAddBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)
        binding.editTaskDeadline.setOnClickListener{
            val dialog = DatePickerFragment()
            dialog.show(this.childFragmentManager, DatePickerFragment.TAG)
        }
        return dialog
    }

    companion object {
        fun newInstance(): TaskAddFragment {
            val dialog = TaskAddFragment()
            return dialog
        }
    }

}
