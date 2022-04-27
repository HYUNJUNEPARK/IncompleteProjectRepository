package com.june.daangnmarket.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager

class UnConnectionDialog(val context: Context) {
    val unConnectionDialog: AlertDialog = AlertDialog.Builder(context)
        .setTitle("네트워크 연결 안됨")
        .setMessage("WIFE 또는 LTE 연결을 확인해주세요")
        .setPositiveButton("취소") { _, _ -> }
        .setNegativeButton("WIFI 연결") { _, _ ->
            connectWifi()
        }
        .create()

    private fun connectWifi() {
        val intent = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK)
        context.startActivity(intent)
    }
}