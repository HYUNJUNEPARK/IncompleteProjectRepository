package com.june.phonenumberbackup.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.Contacts.PhonesColumns.NUMBER
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.june.phonenumberbackup.R
import com.june.phonenumberbackup.databinding.ActivityDownLoadBinding
import com.june.phonenumberbackup.model.ContactInfoModel
import com.june.phonenumberbackup.resolver.AddContact
import com.june.phonenumberbackup.resolver.ContactInfoResolver

class DownloadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownLoadBinding
    lateinit var contactInfoList: MutableList<ContactInfoModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_down_load)
        contactInfoList = ContactInfoResolver().contactInfo(this)

        binding.phoneNumberCountTextView.text = contactInfoList.size.toString()
        Log.d("testLog", "onCreate: $contactInfoList")
    }

    fun saveInfoTest(v: View) {
        AddContact().addContact(this)

    }
}


//Android - ContentProvider 구현 및 예제
//https://codechacha.com/ko/android-contentprovider/