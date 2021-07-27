package com.soares.task.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.soares.task.R
import com.soares.task.databinding.FragmentMainBinding

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
            navController.navigate(MainFragmentDirections.actionMainFragmentToTaskFragment())
        }
    }

    override fun observers() {

    }

    override fun onClick(v: View?) {

    }
}