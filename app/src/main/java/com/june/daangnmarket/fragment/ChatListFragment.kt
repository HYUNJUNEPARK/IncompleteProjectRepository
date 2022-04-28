package com.june.daangnmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.june.daangnmarket.adapter.ChatListAdapter
import com.june.daangnmarket.databinding.FragmentChatListBinding
import com.june.daangnmarket.key.DBKey.Companion.CHILD_CHATROOM
import com.june.daangnmarket.key.DBKey.Companion.DB_USERS
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.firebaseDBReference
import com.june.daangnmarket.model.ChatListModel

class ChatListFragment : BaseFragment() {
    private var _binding: FragmentChatListBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var chatListAdapter: ChatListAdapter
    private val chatRoomList = mutableListOf<ChatListModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mGlideRequestManager = Glide.with(this)

        if (auth?.currentUser == null) {
            binding.noMemberCover.visibility = View.VISIBLE
            binding.noMemberCover.setOnTouchListener { _, _ ->
                true
            }
        } else {
            binding.noMemberCover.visibility = View.INVISIBLE
            initRecyclerView(mGlideRequestManager)
        }
    }

    override fun onResume() {
        super.onResume()
        if (auth?.currentUser != null) {
            chatListAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView(mGlideRequestManager: RequestManager) {
        binding.progressBar.visibility = View.VISIBLE

        chatRoomList.clear()
        chatListAdapter = ChatListAdapter(
            onItemClicked = {
                //TODO 채팅방으로 이동하는 코드
            },
            mGlideRequestManager
        )
        binding.chatListRecyclerView.adapter = chatListAdapter
        binding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)

        val chatDB = firebaseDBReference
            .child(DB_USERS)
            .child(auth?.currentUser!!.uid)
            .child(CHILD_CHATROOM)

        chatDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { dataSnapshot ->
                        val model = dataSnapshot.getValue(ChatListModel::class.java)
                        model ?: return
                        chatRoomList.add(model)
                    }
                    chatListAdapter.submitList(chatRoomList)
                    chatListAdapter.notifyDataSetChanged()
                    _binding?.progressBar?.visibility = View.INVISIBLE
                }

                override fun onCancelled(e: DatabaseError) {
                    Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_SHORT).show()
                    _binding?.progressBar?.visibility = View.INVISIBLE
                }
            })
    }
}