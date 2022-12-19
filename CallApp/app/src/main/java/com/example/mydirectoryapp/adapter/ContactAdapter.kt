package com.example.mydirectoryapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.databinding.ItemContactBinding
import com.example.mydirectoryapp.model.Contact
import com.example.mydirectoryapp.util.Call

class ContactAdapter(context: Context): RecyclerView.Adapter<ContactAdapter.MyHolder>() {
    var contactList = mutableListOf<Contact>()
    val context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    inner class MyHolder(val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {
        //TODO 공부
        lateinit var _contact: Contact

        init {
            binding.callButton.setOnClickListener {
                val phoneNumber = "tel:${_contact?.number}"
                Call(context).call(phoneNumber)
            }

            binding.root.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("content://contacts/people/${_contact?.id}")
                )

                //TODO 연락처가 삭제된 후 UI 를 다시 그려주는 코드가 필요함
                context.startActivity(intent)
            }
        }

        fun setContents(contact: Contact) {
            _contact = contact
            val charRange = IntRange(0, 0)
            binding.contactTextView.text = contact.name.slice(charRange)
            setContactBallColor(binding.contactTextView)
            binding.nameTextView.text = contact.name
            binding.numberTextView.text = contact.number
            when (contact.sim) {
                "1" -> binding.simImage.background = ContextCompat.getDrawable(context, R.drawable.sim_b_home_1_blue)
                "2" -> binding.simImage.background = ContextCompat.getDrawable(context, R.drawable.sim_a_mobile_2_red)
                else -> binding.simImage.background = ContextCompat.getDrawable(context, R.drawable.sim_else)
            }
        }

        private fun setContactBallColor(textView: TextView) {
            when((1..5).random()) {
                1 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_black)
                2 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_blue)
                3 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_green)
                4 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_red)
                5 -> textView.background = ContextCompat.getDrawable(context, R.drawable.profile_background_puple)
            }
        }
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val contact = contactList[position]
        myHolder.setContents(contact)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}