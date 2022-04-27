package com.june.daangnmarket.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.june.daangnmarket.databinding.ActivityStartBinding
import com.june.daangnmarket.dialog.SignInDialog
import com.june.daangnmarket.key.DBKey.Companion.TAG
import com.june.daangnmarket.network.NetworkConnection
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.email

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
        initSignUpButton()
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
            this.finish()
        }
    }

    private fun initSignUpButton() {
        binding.signUpButton.setOnClickListener {
            Log.d(TAG, "initSignInButton: Clicked")
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initOpenSignInDialog() {
        binding.signInBtn.setOnClickListener {
            val myDialog = SignInDialog()
            myDialog.signInDialog(this)
        }
    }
}