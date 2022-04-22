package com.june.daangnmarket.share

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class FirebaseVar: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var storage: FirebaseStorage
        var email: String? = null

        fun initEmail() {
            val currentUser = auth.currentUser
            email = currentUser.email
        }
    }

    override fun onCreate() {
        super.onCreate()

        auth = Firebase.auth
        storage = Firebase.storage
    }
}