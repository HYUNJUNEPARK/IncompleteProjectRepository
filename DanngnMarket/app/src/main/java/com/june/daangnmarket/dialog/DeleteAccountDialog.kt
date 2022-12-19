package com.june.daangnmarket.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.june.daangnmarket.R
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.email

class DeleteAccountDialog {
    fun deleteAccountDialog(activity: FragmentActivity, context: Context) {
        val mDialogView: View = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null)
        val closeButton: Button =  mDialogView.findViewById<Button>(R.id.closeButton)
        val deleteButton: Button = mDialogView.findViewById<Button>(R.id.deleteButton)
        val mDialogBuilder = AlertDialog.Builder(context).setView(mDialogView)
        val mAlertDialog = mDialogBuilder.show()

        closeButton.setOnClickListener {
            mAlertDialog.dismiss()
        }
        deleteButton.setOnClickListener { view ->
            val user = auth?.currentUser!!
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        email = null
                        Toast.makeText(context, "계정 삭제", Toast.LENGTH_SHORT)
                        activity?.finish()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "계정 삭제 실패", Toast.LENGTH_SHORT).show()
                }
        }
    }
}