package com.june.phonenumberbackup.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.june.phonenumberbackup.R
import com.june.phonenumberbackup.permission.Permission

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Permission(this).checkPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //result : true(0) 모든 권한 승인 / false(-1) 권한 중 하나라도 거절
        if (grantResults.all { result -> result == PackageManager.PERMISSION_GRANTED}) //권한을 모두 승인 받았을 때
        {
            Permission(this).permissionGranted()
        }
        else { //권한 승인이 하나라도 거절되었을 때 -> AlertDialog
            Permission(this).permissionDenied()
        }
    }

    fun downloadButtonClicked(v: View) {
        val intent = Intent(this, DownloadActivity::class.java)
        startActivity(intent)
    }

    fun syncButtonClicked(v: View) {
        val intent = Intent(this, SyncActivity::class.java)
        startActivity(intent)
    }
}