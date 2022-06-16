package com.june.phonenumberbackup.permission

import android.Manifest

class PermissionConstant {
    companion object {
        const val permissionRequestCode = 999

        val permissionsArray: Array<String> = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}