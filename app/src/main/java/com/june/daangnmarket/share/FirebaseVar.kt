package com.june.daangnmarket.share

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FirebaseVar: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var storage: FirebaseStorage
        lateinit var articleDBReference: DatabaseReference
        lateinit var imgRef: StorageReference

        var email: String? = null
        var currentUser: FirebaseUser? = null

        fun initEmail() {
            currentUser = auth.currentUser
            email = currentUser!!.email
        }
    }

    override fun onCreate() {
        super.onCreate()

        auth = Firebase.auth
        storage = Firebase.storage
        articleDBReference = Firebase.database.reference

    }
}