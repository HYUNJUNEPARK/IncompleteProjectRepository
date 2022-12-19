package com.june.daangnmarket.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.june.daangnmarket.databinding.ActivitySignupBinding
import com.june.daangnmarket.key.FirebaseVar.Companion.auth

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initSignUpButton()
    }
    private fun initSignUpButton() {
        binding.signUpButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            val email = binding.emailEditView.text.toString()
            val pw = binding.passwordEditView.text.toString()

            Thread {
                auth?.createUserWithEmailAndPassword(email, pw)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            runOnUiThread {
                                binding.progressBar.visibility = View.INVISIBLE
                                Toast.makeText(this, "가입 완료", Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        } else {
                            runOnUiThread {
                                binding.progressBar.visibility = View.INVISIBLE
                                Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }.start()
        }
    }
}