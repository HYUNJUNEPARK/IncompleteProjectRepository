package com.example.mydirectoryapp.util

import android.content.Context
import android.content.Intent
import android.net.Uri

class Call(val context: Context) {
    fun call(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber))
        context.startActivity(intent)
    }
}