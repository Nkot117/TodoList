package com.nkot.todolist.ui.TaskAdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nkot.todolist.R
import com.nkot.todolist.databinding.FragmentTaskAddBinding

class TaskAddFragment : Fragment() {
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskAddBinding.inflate(layoutInflater)
        return binding.root
    }
}