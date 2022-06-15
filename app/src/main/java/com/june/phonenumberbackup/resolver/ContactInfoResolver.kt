package com.june.phonenumberbackup.resolver

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.june.phonenumberbackup.model.ContactInfoModel

class ContactInfoResolver() {
    fun contactInfo(context: Context): MutableList<ContactInfoModel> {
        var contactInfoList = mutableListOf<ContactInfoModel>()

        val uri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val contactArray = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor = context.contentResolver?.query(
            uri,
            contactArray,
            null,
            null,
            null
        )
        while (cursor?.moveToNext() == true) {
            val name = cursor.getString(0)
            val number = cursor.getString(1)
            val contactInfo = ContactInfoModel(name, number)
            contactInfoList.add(contactInfo)
        }
        cursor?.close()
        return contactInfoList


    }
}