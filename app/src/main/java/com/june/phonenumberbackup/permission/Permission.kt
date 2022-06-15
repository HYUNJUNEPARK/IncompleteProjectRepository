package com.june.phonenumberbackup.permission

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.june.phonenumberbackup.permission.PermissionConstant.Companion.permissionRequestCode
import com.june.phonenumberbackup.permission.PermissionConstant.Companion.permissionsArray

class Permission(private val context: Context) {
    fun checkPermissions() {
        //AOS M 버전 미만 권한 요청
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted()
        }
        //AOS M 버전 이상 권한 요청
        else {
            val isAllPermissionGranted: Boolean = permissionsArray.all { permission ->
                context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            }
            if (isAllPermissionGranted) {
                permissionGranted()
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    permissionsArray,
                    permissionRequestCode
                )
            }
        }
    }

    fun permissionGranted() {
        Toast.makeText(context, "모든 권한 승인", Toast.LENGTH_SHORT).show()
    }

    fun permissionDenied() {
        denyDialog()
    }

    private fun denyDialog() {
        AlertDialog.Builder(context)
            .setTitle("권한 설정")
            .setMessage("권한 거절로 인해 일부기능이 제한됩니다.")
            .setPositiveButton("취소") { _, _ -> }
            .setNegativeButton("권한 설정하러 가기") { _, _ ->
                applicationInfo()
            }
            .create()
            .show()
    }

    private fun applicationInfo() {
        val packageUri = Uri.parse("package:${context.packageName}")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri)
        context.startActivity(intent)
    }
}