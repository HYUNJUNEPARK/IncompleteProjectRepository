package com.june.phonenumberbackup.permission

import android.Manifest

class PermissionConstant {
    companion object {
        const val permissionRequestCode = 999

        val permissionsArray: Array<String> = arrayOf(
            Manifest.permission.READ_CONTACTS,
        )
    }
}