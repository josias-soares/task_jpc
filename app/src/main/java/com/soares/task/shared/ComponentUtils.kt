package com.soares.task.shared

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import com.soares.task.R

open class ComponentUtils {
    fun getLoadingDialog(
        activity: Activity,
        setCancellationOnTouchOutside: Boolean
    ): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val customLayout: View = activity.layoutInflater.inflate(R.layout.custom_dialog, null)
        builder.setView(customLayout)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(setCancellationOnTouchOutside)
        return dialog
    }

    fun getAlertDialog(
        activity: Activity,
        message: String,
        setCancellationOnTouchOutside: Boolean
    ): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.apply {
            setMessage(message)
            setPositiveButton(
                "OK"
            ) { dialog, _ -> dialog.dismiss() }
        }

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(setCancellationOnTouchOutside)
        return dialog
    }
}