package com.example.mydirectoryapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mydirectoryapp.util.OnItemClick
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.databinding.ItemKeypadBinding
import com.example.mydirectoryapp.model.Contact

class KeypadAdapter(context: Context, onItemClick: OnItemClick): RecyclerView.Adapter<KeypadAdapter.MyHolder>() {
    var searchList = mutableListOf<Contact>()
    val context: Context = context
    val onItemClick = onItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemKeypadBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyHolder(binding)
    }

    inner class MyHolder(val binding: ItemKeypadBinding): RecyclerView.ViewHolder(binding.root) {
        lateinit var _contact: Contact

        init {
            binding.root.setOnClickListener {
                //TODO Keypad UI callNumberTextView 에 아이템에 해당하는 번호 삽입
                //Adapter -> Fragment 데이터 전달 O
                //Fragment Binding 이 활성화 안되어있음 -> 예외발생
                val number = _contact.number
                onItemClick.onClick(number)
            }
        }

        fun setContents(contact: Contact) {
            _contact = contact
            val charRange = IntRange(0, 0)
            binding.contactTextView.text = contact.name.slice(charRange)
            setContactBallColor(binding.contactTextView)
            binding.numberTextView.text = contact.number
            binding.nameTextView.text = contact.name
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
        val contact = searchList[position]
        myHolder.setContents(contact)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}