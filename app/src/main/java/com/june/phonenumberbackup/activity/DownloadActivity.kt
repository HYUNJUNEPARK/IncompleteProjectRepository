package com.june.phonenumberbackup.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import com.june.phonenumberbackup.R

class DownloadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_load)
    }

    fun getContents(v: View) {
        val uri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val contactArray = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE /*1)HOME, 2)MOBILE, 3)WORK*/,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        )
        val cursor = contentResolver?.query(uri, contactArray, null, null, null)
        while (cursor?.moveToNext() == true) {
            val name = cursor.getString(0)
            val number = cursor.getString(1)
            val sim = cursor.getString(2)
            val id = cursor.getString(3)
            Log.d("testLog", "getContents: name: $name/number: $number/ sim:  $sim/ id: $id")
            //val contact= Contact(name, number, sim, id)
            //contactListAll.add(contact)
        }
        cursor?.close()
    }
}