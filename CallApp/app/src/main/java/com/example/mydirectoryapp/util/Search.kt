package com.example.mydirectoryapp.util

import com.example.mydirectoryapp.activity.MainActivity
import com.example.mydirectoryapp.model.Contact

class Search {
    fun phoneNumber(searchList: MutableList<Contact>, inputNumber: String) {
        for(contact in MainActivity.contactListAll) {
            var contactNumber: String ? = null
            contactNumber = contact.number.replace("-", "")
            contactNumber = contactNumber.replace("(", "")
            contactNumber = contactNumber.replace(")","")
            contactNumber = contactNumber.replace(" ", "")
            if (contactNumber.contains(inputNumber!!)) {
                searchList.add(contact)
            }
        }
    }

    fun letters(searchList: MutableList<Contact>, inputLetters: String) {
        for (contact in MainActivity.contactListAll) {
            var contactName: String ? = null
            contactName = contact.name.replace(" ", "")
            if (contactName.contains(inputLetters, true)) {
                searchList.add(contact)
            }
        }
    }
}