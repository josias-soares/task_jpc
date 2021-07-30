package com.soares.task.ui.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.soares.task.shared.ComponentUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    lateinit var loadingDialog: AlertDialog
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        loadingDialog = ComponentUtils().getLoadingDialog(requireActivity(), false)
        listeners()
        observers()
    }

    abstract fun listeners()
    abstract fun observers()

}