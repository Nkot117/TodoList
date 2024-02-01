package com.nkot.todolist.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.nkot.todolist.databinding.FragmentCustomDialogBinding

class CustomDialogFragment : DialogFragment() {
    private var _binding: FragmentCustomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCustomDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)
        return dialog
    }

    companion object {
        fun newInstance(): CustomDialogFragment {
            return CustomDialogFragment()
        }
    }


}
