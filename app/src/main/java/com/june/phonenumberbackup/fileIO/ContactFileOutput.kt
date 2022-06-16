package com.june.phonenumberbackup.fileIO

import android.content.Context
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.OutputStreamWriter

class ContactFileOutput(private val context: Context) {
    fun writeFile() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            try {
                val filesDir = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
                //TODO 하드코딩 제거
                val filesName = "CONTACT_BACKUP.txt"

                File(filesDir, filesName).let {
                    val writeStream: OutputStreamWriter = it.writer()
                    //TODO 연락처 이름 / 전화번호 저장
                    writeStream.write("test3")
                    writeStream.flush()
                }
                Toast.makeText(context, "파일 생성 완료", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "파일 생성 실패 : $e", Toast.LENGTH_SHORT).show()
                Log.e("TestLog", "writeFile: $e")
            }
        } else {
            Toast.makeText(context, "외장 메모리 사용 불가", Toast.LENGTH_SHORT).show()
        }
    }
}