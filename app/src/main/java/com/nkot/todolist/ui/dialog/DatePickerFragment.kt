package com.nkot.todolist.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.nkot.todolist.R
import com.nkot.todolist.ui.TaskAdd.TaskAddFragment
import com.nkot.todolist.ui.TaskEdit.TaskEditFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var editTextId: Int? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        editTextId = arguments?.getInt(EDIT_TEXT_ID)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            this,
            year,
            month,
            day
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val editDeadlineTextView = editTextId?.let {
            when (val fragment = parentFragment) {
                is TaskAddFragment -> fragment.dialog?.findViewById<EditText>(it)
                is TaskEditFragment -> fragment.view?.findViewById<EditText>(it)
                else -> null
            }
        }
        val deadlineText = getString(R.string.deadline_text_format, year, month + 1, dayOfMonth)
        editDeadlineTextView?.setText(deadlineText)
    }

    companion object {
        const val TAG = "DatePickerFragment"
        const val EDIT_TEXT_ID = "editTextId"
        fun newInstance(editTextId: Int): DatePickerFragment {
            val args = Bundle().also {
                it.putInt(EDIT_TEXT_ID, editTextId)
            }

            val datePickerDialog = DatePickerFragment().also {
                it.arguments = args
            }

            return datePickerDialog
        }
    }

}
