package com.june.phonenumberbackup.activity

import android.os.Environment
import java.io.File

class Constant {
    companion object {
        private val FILE_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        //TODO 하드코딩 제거
        private const val FILE_NAME = "CONTACT_BACKUP_FILE.txt"

        fun contactFile(): File {
            return File(FILE_DIR, FILE_NAME)
        }

        //val file = File(filesDir, filesName)
    }
}