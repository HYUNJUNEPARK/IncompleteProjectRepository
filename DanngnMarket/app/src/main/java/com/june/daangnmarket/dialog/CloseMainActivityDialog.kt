package com.june.daangnmarket.dialog

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.june.daangnmarket.R

class CloseMainActivityDialog {
    fun closeMainActivityDialog(activity: Activity) {
        val mDialogView: View = LayoutInflater.from(activity).inflate(R.layout.dialog_close_mainactivity, null)
        val closeAppButton: Button = mDialogView.findViewById<Button>(R.id.closeAppButton)
        val closeDialogButton: Button = mDialogView.findViewById<Button>(R.id.closeDialogButton)
        val mDialogBuilder = AlertDialog.Builder(activity).setView(mDialogView)
        val mAlertDialog = mDialogBuilder.show()

        closeAppButton.setOnClickListener {
            activity?.finish()
        }
        closeDialogButton.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}
