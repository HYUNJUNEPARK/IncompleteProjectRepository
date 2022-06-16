package com.june.phonenumberbackup.fileIO

import android.content.Context
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.view.View
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class ContactFileIO(private val context: Context) {
    companion object {
        val filesDir = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
        //TODO 하드코딩 제거
        val filesName = "CONTACT_BACKUP1.txt"
        val file = File(filesDir, filesName)
    }



    fun writeFile(name: String, phoneNumber: String) {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            try {
                //TODO 비동기 작업으로 뺄것
                val fileWriter = FileWriter(file, true)
                val name = name
                val phoneNumber = phoneNumber
                val contact = "$name,$phoneNumber \n"
                fileWriter.write(contact)
                fileWriter.close()

            } catch (e: Exception) {
                Toast.makeText(context, "파일 생성 실패 : $e", Toast.LENGTH_SHORT).show()
                Log.e("TestLog", "writeFile: $e")
                return
            }
        } else {
            Toast.makeText(context, "외부 저장소 사용 불가", Toast.LENGTH_SHORT).show()
        }
    }

    //TODO 한줄 씩 읽는 과정에서 첫번째 전화 번호를 반복해서 출력하는 무한루프에 빠짐
    // 이거 해결해야함 !!!!!!!!
    fun readFile() {
        val reader = BufferedReader(
            FileReader(file)
        )
        val contact = reader.readLine()
        while (contact != null) {
            Log.d("testLog", "readFile: $contact \n")
        }
        reader.close()

    }
}