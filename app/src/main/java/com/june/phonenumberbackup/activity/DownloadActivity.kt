package com.june.phonenumberbackup.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.june.phonenumberbackup.R
import com.june.phonenumberbackup.contact_access.ReadContact
import com.june.phonenumberbackup.databinding.ActivityDownLoadBinding
import com.june.phonenumberbackup.fileIO.ContactFileIO
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

    fun fileOutputButtonClicked(v: View) {
        //TODO 파일 존재 여부 체크
        for (contact in contactInfoList) {
            val name = contact.name
            val phoneNumber = contact.phoneNumber
            ContactFileIO(this).writeFile(name, phoneNumber)
        }
    }
}
