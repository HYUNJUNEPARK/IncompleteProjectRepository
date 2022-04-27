package com.june.daangnmarket.dialog

import android.app.Activity
import android.app.AlertDialog
import com.june.daangnmarket.key.RequestCode

class PermissionDialog {
    fun showPermissionPopup(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                activity.requestPermissions(
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    RequestCode.REQUEST_READ_EXTERNAL_STORAGE
                )
            }
            .create()
            .show()
    }
}