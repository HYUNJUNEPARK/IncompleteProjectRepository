package com.example.mydirectoryapp.permission

import android.Manifest
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

class Permission(private val context: Context) {
    //TODO 필요 권한 배열 커스텀
    private val permissionRequestCode = 999
    private val permissionsArray: Array<String> = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_PHONE_NUMBERS

    )

    //TODO 권한 요청이 필요한 시점에서 호출해 사용
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
        //TODO 모든 권한 승인 시 상황에 맞는 코드 작성
        Toast.makeText(context, "모든 권한 승인 완료", Toast.LENGTH_SHORT).show()
    }

    fun permissionDenied() {
        //TODO 권한이 하나라도 거절되었을 때 상황에 맞는 코드 작성
        AlertDialog.Builder(context)
            .setTitle("권한 설정")
            .setMessage("권한 거절로 인해 일부기능이 제한됩니다.")
            .setPositiveButton("종료") { _, _ ->
                val activity = context as Activity
                activity.finish()
            }
            .setNegativeButton("권한 설정하러 가기") { _, _ ->
                applicationInfo()
            }
            .create()
            .show()
    }

    //TODO 수동으로 권한 설정이 필요할 때 호출
    private fun applicationInfo() {
        val packageUri = Uri.parse("package:${context.packageName}")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri)
        context.startActivity(intent)
    }
}