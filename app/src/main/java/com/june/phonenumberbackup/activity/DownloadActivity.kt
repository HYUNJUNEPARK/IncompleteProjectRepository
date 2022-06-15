package com.june.phonenumberbackup.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.june.phonenumberbackup.R
import com.june.phonenumberbackup.contact_access.ReadContact
import com.june.phonenumberbackup.contact_access.SyncContact
import com.june.phonenumberbackup.databinding.ActivityDownLoadBinding
import com.june.phonenumberbackup.model.ContactInfoModel

class DownloadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownLoadBinding
    lateinit var contactInfoList: MutableList<ContactInfoModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_down_load)
        contactInfoList = ReadContact().readContactInfo(this)
        binding.phoneNumberCountTextView.text = contactInfoList.size.toString()
    }

    fun saveInfoTest(v: View) {
        SyncContact(this).syncContactInfo("테스트 이름", "010-4444-3132")
    }
}
