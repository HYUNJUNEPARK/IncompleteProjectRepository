package com.june.daangnmarket.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.june.daangnmarket.R
import com.june.daangnmarket.databinding.ActivityStartBinding
import com.june.daangnmarket.network.NetworkConnection
import com.june.daangnmarket.share.FirebaseVar.Companion.auth
import com.june.daangnmarket.share.FirebaseVar.Companion.email
import com.june.daangnmarket.share.FirebaseVar.Companion.initEmail

class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private val networkCheck: NetworkConnection by lazy {
        NetworkConnection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        networkCheck.register()

        initSignInWithoutAuthButton()
        initSignInButton()
        initOpenSignInDialog()
    }

    override fun onDestroy() {
        super.onDestroy()

        networkCheck.unregister()
    }

    private fun initSignInWithoutAuthButton() {
        binding.sigInWithoutAuthButton.setOnClickListener {
            auth.signOut()
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
                    auth.signInWithEmailAndPassword(email, pw)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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