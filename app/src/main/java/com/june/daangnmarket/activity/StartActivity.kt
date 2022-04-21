package com.june.daangnmarket.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.june.daangnmarket.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initSignInWithoutAuthButton()
        initSignInButton()
    }
    private fun initSignInWithoutAuthButton() {
        binding.sigInWithoutAuthButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initSignInButton() {
        binding.signInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}