package com.soares.task.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.soares.task.R
import com.soares.task.databinding.FragmentMainBinding
import com.soares.task.domain.models.Task
import com.soares.task.shared.ComponentUtils
import com.soares.task.ui.adapters.TaskAdapter
import com.soares.task.ui.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainFragment : BaseFragment(), TaskAdapter.OnItemTaskClickListener {
    private var currentPosition: Int = 0
    private val listTask: MutableList<Task> = arrayListOf()
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_main, container, false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this@MainFragment

        taskAdapter = TaskAdapter(listTask, this)
        binding.adapter = taskAdapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun listeners() {
        binding.fab.setOnClickListener {
            navController.navigate(MainFragmentDirections.actionMainFragmentToTaskFragment(Task()))
        }
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getAll()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observers() {
        viewModel.apply {
            tasks.observe(this@MainFragment, {
                listTask.clear()
                listTask.addAll(it)

                taskAdapter.notifyDataSetChanged()
            })

            changedTask.observe(this@MainFragment, {
                taskAdapter.notifyItemChanged(currentPosition)
            })

            loading.observe(this@MainFragment, { show ->
                binding.swipeLayout.isRefreshing = show
            })

            errorMessage.observe(this@MainFragment, {
                loadingDialog.dismiss()
                ComponentUtils().getAlertDialog(requireActivity(), it, false)
            })
        }
    }

    override fun onEdit(task: Task) {
        navController.navigate(MainFragmentDirections.actionMainFragmentToTaskFragment(task))
    }


    override fun onRemove(task: Task) {
        viewModel.deleteTask(task)
    }


    override fun onComplete(task: Task, position: Int) {
        currentPosition = position
        task.complete = !task.complete
        viewModel.completeTask(task)
    }
}