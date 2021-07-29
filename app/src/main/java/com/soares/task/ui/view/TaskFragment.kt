package com.soares.task.ui.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.soares.task.R
import com.soares.task.databinding.FragmentTaskBinding
import com.soares.task.domain.models.Task
import com.soares.task.shared.ComponentUtils
import com.soares.task.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalCoroutinesApi
class TaskFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var loadingDialog: AlertDialog
    private val viewModel: TaskViewModel by viewModels()
    private val args: TaskFragmentArgs by navArgs()

    private lateinit var binding: FragmentTaskBinding
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val currentTask = MutableLiveData<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_task, container, false
        )

        args.task.let {
            viewModel.setTask(it)
        }

        loadingDialog = ComponentUtils().getLoadingDialog(requireActivity(), false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this@TaskFragment

        return binding.root
    }

    override fun listeners() {
        binding.spinnerPriority.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentTask.value?.let {
                    it.priorityId = position
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.buttonSave.setOnClickListener {
            currentTask.value?.let {
                viewModel.setTask(it)
                viewModel.saveTask()
            }
        }

        binding.editDescription.doOnTextChanged { text, _, _, _ ->
            currentTask.value?.let {
                it.description = text.toString().trim()
            }
        }

        binding.buttonDate.setOnClickListener {
            showDatePicker()
        }
    }

    override fun observers() {
        currentTask.observe(this, {

            it?.let {
                binding.spinnerPriority.setSelection(it.priorityId)
                binding.task = it

                binding.buttonSave.isEnabled =
                    (it.description.isNotEmpty() && it.dueDate.isNotEmpty())
            }

        })

        viewModel.apply {
            loading.observe(this@TaskFragment, { show ->
                if (show) loadingDialog.show() else loadingDialog.dismiss()
            })

            task.observe(this@TaskFragment, {
                currentTask.postValue(it)
            })

            errorMessage.observe(this@TaskFragment, {
                loadingDialog.dismiss()
                ComponentUtils().getAlertDialog(requireActivity(), it, false)
            })
        }
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        DatePickerDialog(
            requireActivity(),
            this,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        currentTask.value?.let {
            it.dueDate = dateFormat.format(calendar.time)
        }
    }
}