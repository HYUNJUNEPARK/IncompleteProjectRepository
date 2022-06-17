package com.june.phonenumberbackup.fileIO

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.june.phonenumberbackup.activity.Constant.Companion.contactFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*

class ContactFileIO(private val context: Context) {
    fun writeFile(name: String, phoneNumber: String) {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val file = contactFile()
                    val fileWriter = FileWriter(file, true)
                    val name = name
                    val phoneNumber = phoneNumber
                    val contact = "$name,$phoneNumber \n"
                    fileWriter.write(contact)
                    fileWriter.close()
                    //TODO 파일 생성 완료 후에는 프로그래스 바 OFF
                }
            } catch (e: Exception) {
                Toast.makeText(context, "파일 생성 실패 : $e", Toast.LENGTH_SHORT).show()
                Log.e("TestLog", "writeFile: $e")
                return
            }
        } else {
            Toast.makeText(context, "외부 저장소 사용 불가", Toast.LENGTH_SHORT).show()
        }
    }

    fun readFile() {
        try {
            val file = contactFile()
            val inputStream = FileInputStream(file)
            val reader = BufferedReader(
                InputStreamReader(inputStream)
            )
            var contact = reader.readLine()

            while (contact != null) {
                //TODO 폰에 전화번호 저장하려면 여기서 실행
                contact = reader.readLine()
            }
        }
        catch (e: Exception) {
            Toast.makeText(context, "파일 로드 실패 : $e", Toast.LENGTH_SHORT).show()
        }
    }
}




















