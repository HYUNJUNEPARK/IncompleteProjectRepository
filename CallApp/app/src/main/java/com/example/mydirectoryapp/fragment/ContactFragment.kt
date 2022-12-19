package com.example.mydirectoryapp.fragment

import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.activity.MainActivity.Companion.contactListAll
import com.example.mydirectoryapp.adapter.ContactAdapter
import com.example.mydirectoryapp.databinding.FragmentContactBinding
import com.example.mydirectoryapp.model.Contact
import com.example.mydirectoryapp.util.DeviceInfo
import com.example.mydirectoryapp.util.Search
import com.example.mydirectoryapp.vm.ContactVM
import java.util.regex.Pattern
import kotlin.system.measureTimeMillis

class ContactFragment : BaseFragment<FragmentContactBinding>(R.layout.fragment_contact) {
    private val contactVM: ContactVM by viewModels()

    companion object {
        const val PATTERN_KOREAN = "^[가-힣]*\$"
        const val PATTERN_NUMBER = "^[0-9]*\$"
        const val PATTERN_ENGLISH = "^[a-zA-Z]*\$"
    }

    override fun initView() {
        super.initView()

        contactListAll.clear()

        initDeviceContact()
        initRecyclerView(contactListAll)
        initSimButtons()
        initSearchView()
        initDeviceInfo()
    }

    fun initDeviceContact() {
        val uri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val contactArray = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE /*1)HOME, 2)MOBILE, 3)WORK*/,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        )
        val cursor = context?.contentResolver?.query(uri, contactArray, null, null, null)
        while (cursor?.moveToNext() == true) {
            val name = cursor.getString(0)
            val number = cursor.getString(1)
            val sim = cursor.getString(2)
            val id = cursor.getString(3)
            val contact= Contact(name, number, sim, id)
            contactListAll.add(contact)
        }
        cursor?.close()
    }

    private fun initRecyclerView(contactList: MutableList<Contact>) {
        //TODO 뷰모델안에 있는 연락처 리스트로 초기화 해줌
        val adapter = ContactAdapter(requireContext())
        adapter.contactList = contactList

        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun initSearchView() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (Pattern.matches(PATTERN_KOREAN, query) || Pattern.matches(PATTERN_ENGLISH, query)) {
                    searchLetters(query)
                }
                else if (Pattern.matches(PATTERN_NUMBER, query)) {
                    searchNumber(query)
                }
                else {
                    Toast.makeText(requireContext(), getString(R.string.wrong_input_type_message), Toast.LENGTH_SHORT).show()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun searchLetters(query: String) {
        val searchList = mutableListOf<Contact>()
        Search().letters(searchList, query)
        initRecyclerView(searchList) //refresh
    }

    private fun searchNumber(query: String?) {
        val searchList = mutableListOf<Contact>()
        Search().phoneNumber(searchList, query!!)
        initRecyclerView(searchList) //refresh
    }

    private fun initDeviceInfo() {
        binding.userNumberTextView.text = "내 프로필(${DeviceInfo(requireContext()).devicePhoneNumber()})"
    }

    //TODO VM 써야하는 이유가 생김!
    //TODO contactListAll 을 VM 에서 관리 시키고 싶음
    //VM 으로 이동시킴 ? -> 굳이 ?
    private fun initSimButtons() {
        binding.allButton.setOnClickListener {
            initRecyclerView(contactListAll)
        }
        binding.simBButton.setOnClickListener {
            val contactListSimB = mutableListOf<Contact>()
            for (i in contactListAll) {
                if (i.sim == "1") contactListSimB.add(i)
            }
            initRecyclerView(contactListSimB)
        }
        binding.simAButton.setOnClickListener {
            val contactListSimA = mutableListOf<Contact>()
            for (i in contactListAll) {
                if (i.sim == "2") contactListSimA.add(i)
            }
            initRecyclerView(contactListSimA)
        }
        binding.simElseButton.setOnClickListener {
            val contactListSimElse = mutableListOf<Contact>()
            for (i in contactListAll) {
                if (i.sim != "1" && i.sim != "2") contactListSimElse.add(i)
            }
            initRecyclerView(contactListSimElse)
        }
    }
}