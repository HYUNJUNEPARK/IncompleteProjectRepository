package com.june.daangnmarket.dialog

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.june.daangnmarket.R
import com.june.daangnmarket.activity.MainActivity
import com.june.daangnmarket.activity.SignInActivity
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.initEmail

class SignInDialog {
    fun signInDialog(activity: SignInActivity) {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_signin, null)
        val mDialogBuilder = AlertDialog.Builder(activity).setView(mDialogView)
        val mDialog = mDialogBuilder.show()
        val closeButton = mDialogView.findViewById<Button>(R.id.closeButton)
        val signInButton = mDialogView.findViewById<Button>(R.id.signInBtn)

        signInButton.setOnClickListener {
            val emailView = mDialogView.findViewById<EditText>(R.id.emailEditText)
            val pwView = mDialogView.findViewById<EditText>(R.id.passwordEditText)
            val email = emailView.text.toString()
            val pw = pwView.text.toString()

            if (email == "" || pw == "") {
                Toast.makeText(activity, "정보를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Thread {
                auth.signInWithEmailAndPassword(email, pw)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            initEmail()
                            val intent = Intent(activity, MainActivity::class.java)
                            activity.startActivity(intent)
                            mDialog.dismiss()
                            activity.finish()
                        } else {
                            activity.runOnUiThread {
                                Toast.makeText(activity, "오류 발생", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }.start()
        }
        closeButton.setOnClickListener {
            mDialog.dismiss()
        }
    }
}