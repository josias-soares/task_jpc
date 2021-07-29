package com.soares.task.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.soares.task.R
import com.soares.task.databinding.FragmentMainBinding
import com.soares.task.domain.models.Task

class MainFragment : BaseFragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_main, container, false
        )

        //binding.viewModel = viewModel
        binding.lifecycleOwner = this@MainFragment


        return binding.root
    }

    override fun listeners() {
        binding.txtFragTask.setOnClickListener {
            navController.navigate(MainFragmentDirections.actionMainFragmentToTaskFragment(task = Task(1,"Teste", 2, "25/06/2021", true)))
        }
    }

    override fun observers() {

    }
}