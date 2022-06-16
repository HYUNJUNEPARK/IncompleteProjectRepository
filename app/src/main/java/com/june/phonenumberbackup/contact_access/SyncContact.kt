package com.june.phonenumberbackup.contact_access

import android.content.ContentProviderOperation
import android.content.Context
import android.provider.ContactsContract
import android.widget.Toast
import java.lang.Exception

//Ref : https://gist.github.com/Antarix/d13f84c051f2f5b8d47b
//Description : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=aiger&logNo=100143481405
class SyncContact(private val context: Context) {
    private lateinit var ops: ArrayList<ContentProviderOperation>
    private lateinit var displayName: String
    private lateinit var phoneNumber: String

    fun syncContactInfo(displayName: String, phoneNumber: String ) {
        ops = ArrayList()
        this.displayName = displayName
        this.phoneNumber = phoneNumber

        //RawContacts : 사용자 계정과 유형을 기준으로, 한 사람에 대한 데이터 요약이 들어 있는 행
        ops.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        insertDisplayName()
        insertPhoneNumber()

        //Asking the Contact provider to create a new contact
        try {
            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Exception: " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertDisplayName() {
        if (displayName != null) {
            ops.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        displayName
                    )
                    .build()
            )
        }
    }

    private fun insertPhoneNumber() {
        if (phoneNumber != null) {
            ops.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        phoneNumber
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                    )
                    .build()
            )
        }
    }
}