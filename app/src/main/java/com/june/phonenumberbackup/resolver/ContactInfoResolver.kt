package com.june.phonenumberbackup.resolver

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract

class ContactInfoResolver() {
    fun mCursor(context: Context): Cursor? {
        val uri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val contactArray = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE /*1)HOME, 2)MOBILE, 3)WORK*/,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        )
        val cursor = context.contentResolver?.query(
            uri,
            contactArray,
            null,
            null,
            null
        )
        return cursor
    }






//    while (cursor?.moveToNext() == true) {
//        val name = cursor.getString(0)
//        val number = cursor.getString(1)
//        val sim = cursor.getString(2)
//        val id = cursor.getString(3)
//        val contact= Contact(name, number, sim, id)
//        contactListAll.add(contact)
//    }
//    cursor?.close()
//

}