package com.example.mydirectoryapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager

class DeviceInfo(val context: Context) {
    @SuppressLint("MissingPermission")
    fun devicePhoneNumber(): String {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.line1Number ?: "null"
    }
}