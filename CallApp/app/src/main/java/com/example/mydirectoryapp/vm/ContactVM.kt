package com.example.mydirectoryapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydirectoryapp.model.Contact

class ContactVM: ViewModel() {
    //연락처 리스트의 길이 -> 옵저버가 관찰함
    private val _currentContactListSize = MutableLiveData<Int>()
    val currentContactListSize: LiveData<Int>
        get() = _currentContactListSize

    //실제 연락처 리스트
    private val _contactList = mutableListOf<Contact>()
    val contactList: MutableList<Contact>
       get() = _contactList

    init {
        _currentContactListSize.value = contactList.size
    }




}