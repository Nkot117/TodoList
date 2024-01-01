package com.nkot.todolist.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.nkot.todolist.R
import com.nkot.todolist.ui.TaskAdd.TaskAddFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
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
        val fragment = parentFragment as TaskAddFragment
        val editDeadlineTextView = fragment.dialog?.findViewById<EditText>(R.id.edit_task_deadline)
        val deadlineText = getString(R.string.deadline_text_format, year, month + 1, dayOfMonth)
        editDeadlineTextView?.setText(deadlineText)
    }

    companion object {
        const val TAG = "DatePickerFragment"
        fun newInstance(): DatePickerFragment {
            return DatePickerFragment()
        }
    }

}
