package com.june.phonenumberbackup.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.june.phonenumberbackup.R
import com.june.phonenumberbackup.activity.Constant.Companion.contactFile
import com.june.phonenumberbackup.databinding.ActivitySyncBinding

class SyncActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySyncBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sync)

        val file = contactFile()
        if (file.exists()) {
            binding.syncButton.isEnabled = true
        }

    }
}