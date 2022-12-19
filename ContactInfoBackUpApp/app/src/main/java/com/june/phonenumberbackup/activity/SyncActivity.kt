package com.june.phonenumberbackup.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.june.phonenumberbackup.R
import com.june.phonenumberbackup.activity.FileConstant.Companion.isFile
import com.june.phonenumberbackup.databinding.ActivitySyncBinding
import com.june.phonenumberbackup.fileIO.ContactFileIO

class SyncActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySyncBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sync)
        //동기화할 수 있는 파일이 있다면 동기화 버튼 활성화
        if (isFile()) {
            binding.syncButton.isEnabled = true
        }
    }

    fun syncButtonClicked(v: View) {
        ContactFileIO(this).readFile()
    }
}