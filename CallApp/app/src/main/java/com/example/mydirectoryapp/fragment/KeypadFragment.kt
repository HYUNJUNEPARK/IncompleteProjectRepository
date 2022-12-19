package com.example.mydirectoryapp.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.activity.MainActivity.Companion.contactListAll
import com.example.mydirectoryapp.adapter.KeypadAdapter
import com.example.mydirectoryapp.databinding.FragmentKeyPadBinding
import com.example.mydirectoryapp.model.Contact
import com.example.mydirectoryapp.util.Call
import com.example.mydirectoryapp.util.OnItemClick
import com.example.mydirectoryapp.util.Search

class KeypadFragment : BaseFragment<FragmentKeyPadBinding>(R.layout.fragment_key_pad), View.OnClickListener, OnItemClick {
    override fun initView() {
        initButton()
        initRecyclerView(contactListAll)
        initCallNumberView()
    }

    override fun onClick(number: String) {
        binding.callNumberTextView.text = number
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.button0 -> buttonClicked("0")
            R.id.button1 -> buttonClicked("1")
            R.id.button2 -> buttonClicked("2")
            R.id.button3 -> buttonClicked("3")
            R.id.button4 -> buttonClicked("4")
            R.id.button5 -> buttonClicked("5")
            R.id.button6 -> buttonClicked("6")
            R.id.button7 -> buttonClicked("7")
            R.id.button8 -> buttonClicked("8")
            R.id.button9 -> buttonClicked("9")
            R.id.buttonStar -> buttonClicked("*")
            R.id.buttonHash -> buttonClicked("#")
            R.id.buttonDelete -> deleteButtonClicked()
            R.id.buttonCall -> {
                val phoneNumber = "tel:${binding.callNumberTextView.text}"
                Call(requireContext()).call(phoneNumber)
            }
        }
    }

    private fun initButton() {
        val buttonList = arrayOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9, binding.buttonStar, binding.buttonHash,
            binding.buttonCall, binding.buttonDelete
        )
        for (button in buttonList) {
            button.setOnClickListener(this)
        }
    }

    private fun buttonClicked(number: String) {
        binding.callNumberTextView.append(number)
    }

    private fun deleteButtonClicked() {
        var numbers = binding.callNumberTextView.text
        if (numbers.isNotEmpty()){
            numbers =  numbers.substring(0, numbers.length -1)
            binding.callNumberTextView.text = numbers
        }
    }

    private fun initCallNumberView() {
        binding.callNumberTextView.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(inputNumber: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchList = mutableListOf<Contact>()
                Search().phoneNumber(searchList, inputNumber.toString())
                initRecyclerView(searchList)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    private fun initRecyclerView(contactList: MutableList<Contact>) {
        val adapter = KeypadAdapter(requireContext(), this )
        adapter.searchList = contactList
        binding.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
    }
}