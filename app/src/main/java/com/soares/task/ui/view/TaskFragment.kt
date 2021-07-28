package com.soares.task.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.soares.task.R
import com.soares.task.databinding.FragmentTaskBinding
import com.soares.task.ui.viewmodel.TaskViewModel

class TaskFragment : BaseFragment() {
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_task, container, false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this@TaskFragment

        return binding.root
    }

    override fun listeners() {
        binding.buttonSave.setOnClickListener {
            navController.navigateUp()
        }
    }

    override fun observers() {

    }

    override fun onClick(v: View?) {

    }
}