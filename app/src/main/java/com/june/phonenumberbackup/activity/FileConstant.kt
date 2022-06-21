package com.june.phonenumberbackup.activity

import android.os.Environment
import java.io.File

class FileConstant {
    companion object {
        private val FILE_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        private const val FILE_NAME = "CONTACT_BACKUP_FILE0.txt"

        fun contactFile(): File {
            return File(FILE_DIR, FILE_NAME)
        }

        fun isFile(): Boolean {
            val file = File(FILE_DIR, FILE_NAME)
            return file.exists()
        }
    }
}