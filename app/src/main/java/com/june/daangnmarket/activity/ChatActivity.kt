package com.june.daangnmarket.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.june.daangnmarket.adapter.ChatItemAdapter
import com.june.daangnmarket.databinding.ActivityChatDetailBinding
import com.june.daangnmarket.key.DBKey.Companion.CHAT_KEY
import com.june.daangnmarket.key.DBKey.Companion.DB_CHATS
import com.june.daangnmarket.key.DBKey.Companion.TAG
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.firebaseDBReference
import com.june.daangnmarket.model.ChatItemModel

class ChatActivity : AppCompatActivity() {
    private val binding by lazy { ActivityChatDetailBinding.inflate(layoutInflater) }
    private val chatList = mutableListOf<ChatItemModel>()
    private val adapter = ChatItemAdapter()
    private lateinit var chatKey: String
    private lateinit var chatDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        chatKey = intent.getStringExtra(CHAT_KEY).toString()
        chatDB = firebaseDBReference
            .child(DB_CHATS)
            .child("$chatKey")

        initRecyclerView()
        initChatLog()


        binding.sendButton.setOnClickListener {
            Log.d(TAG, "onChildAdded: aaaaaaa")


            val chatItem = ChatItemModel(
                senderId = auth.currentUser.uid,
                message = binding.messageEditText.text.toString(),
                sendTime = System.currentTimeMillis()
            )
//            val chatDB = firebaseDBReference
//                .child(DB_CHATS)
//                .child("$chatKey")
            chatDB
                .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chatItem = snapshot.getValue(ChatItemModel::class.java)
                    chatItem ?: return

                    chatList.add(chatItem)
                    adapter.submitList(chatList)

                    //adapter.notifyDataSetChanged()
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { }
                override fun onChildRemoved(snapshot: DataSnapshot) { }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }
                override fun onCancelled(error: DatabaseError) { }
            })

            chatDB
                .push()
                .setValue(chatItem)
        }
    }

    private fun initRecyclerView() {
        binding.chatRecyclerView.adapter = adapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initChatLog() {
        chatDB
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chatItem = snapshot.getValue(ChatItemModel::class.java)
                    chatItem ?: return

                    chatList.add(chatItem)
                    adapter.submitList(chatList)
                    adapter.notifyDataSetChanged()
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { }
                override fun onChildRemoved(snapshot: DataSnapshot) { }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }
                override fun onCancelled(error: DatabaseError) { }
            })
    }
}