package com.soares.task.ui.view

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.DatePicker
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
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
    private val viewModel: TaskViewModel by viewModels()
    private val args: TaskFragmentArgs by navArgs()

    private lateinit var binding: FragmentTaskBinding
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private lateinit var currentTask: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_task, container, false
        )

        args.task.let {
            currentTask = it

            binding.task = it

            binding.spinnerPriority.setSelection(it.priorityId)
            binding.buttonDate.text = it.dueDate

            enableButton()
        }

        activity?.title = if (binding.task!!.id > 0) "Edit" else "New"

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
                currentTask.priorityId = position
                enableButton()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.checkComplete.setOnCheckedChangeListener { _, isChecked ->
            currentTask.complete = isChecked
        }

        binding.buttonSave.setOnClickListener {
            currentTask?.apply {
                complete = binding.checkComplete.isChecked
                description = binding.editDescription.text.toString()

                viewModel.setTask(this)
                viewModel.saveTask()
            }

        }

        binding.editDescription.doOnTextChanged { text, _, _, _ ->
            currentTask.description = text.toString().trim()
            enableButton()
        }

        binding.buttonDate.setOnClickListener {
            showDatePicker()
        }
    }

    override fun observers() {
        viewModel.apply {
            loading.observe(this@TaskFragment, { show ->
                if (show) loadingDialog.show() else loadingDialog.dismiss()
            })

            createdTask.observe(this@TaskFragment, {
                val dialog = ComponentUtils().getAlertDialog(requireActivity(), "Task Added", false)
                dialog.setOnDismissListener {
                    navController.navigateUp()
                }
                dialog.show()

                hideKeyboard(requireContext(), view?.parent as ViewGroup)

            })

            errorMessage.observe(this@TaskFragment, {
                loadingDialog.dismiss()
                ComponentUtils().getAlertDialog(requireActivity(), it, false)
            })
        }
    }

    fun hideKeyboard(context: Context, viewGroup: ViewGroup) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(viewGroup.windowToken, 0)
        } catch (e: Exception) {

        }
    }

    private fun enableButton() {
        currentTask.apply {
            binding.buttonSave.isEnabled =
                (description.isNotEmpty() && dueDate.isNotEmpty() && priorityId > 0)
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

        currentTask.apply {
            dueDate = dateFormat.format(calendar.time)
            binding.buttonDate.text = dueDate
        }
        enableButton()
    }
}