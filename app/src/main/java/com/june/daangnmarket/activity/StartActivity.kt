package com.june.daangnmarket.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.june.daangnmarket.R
import com.june.daangnmarket.databinding.ActivityStartBinding
import com.june.daangnmarket.share.FirebaseVar
import com.june.daangnmarket.share.FirebaseVar.Companion.email
import com.june.daangnmarket.share.FirebaseVar.Companion.initEmail

class StartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initSignInWithoutAuthButton()
        initSignInButton()
        initOpenSignInDialog()
    }

    private fun initSignInWithoutAuthButton() {
        binding.sigInWithoutAuthButton.setOnClickListener {
            email = null
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initSignInButton() {
        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initOpenSignInDialog() {
        binding.signInBtn.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_signin, null)
            val mDialogBuilder = AlertDialog.Builder(this).setView(mDialogView)
            val mDialog = mDialogBuilder.show()
            val closeButton = mDialogView.findViewById<Button>(R.id.closeButton)
            val signInButton = mDialogView.findViewById<Button>(R.id.signInBtn)

            signInButton.setOnClickListener {
                val emailView = mDialogView.findViewById<EditText>(R.id.emailEditText)
                val pwView = mDialogView.findViewById<EditText>(R.id.passwordEditText)
                val email = emailView.text.toString()
                val pw = pwView.text.toString()

                if (email == "" || pw == "") {
                    Toast.makeText(this, "정보를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Thread {
                    FirebaseVar.auth.signInWithEmailAndPassword(email, pw)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                runOnUiThread {
                                    Toast.makeText(this, "로그인 완료", Toast.LENGTH_SHORT).show()
                                }
                                initEmail()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                mDialog.dismiss()
                            } else {
                                runOnUiThread {
                                    Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show()
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
}