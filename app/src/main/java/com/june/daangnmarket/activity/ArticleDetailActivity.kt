package com.june.daangnmarket.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.june.daangnmarket.R
import com.june.daangnmarket.databinding.ActivityArticleDetailBinding
import com.june.daangnmarket.key.DBKey.Companion.CHILD_CHATROOM
import com.june.daangnmarket.key.DBKey.Companion.DB_USERS
import com.june.daangnmarket.key.DBKey.Companion.TAG
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.firebaseDBReference
import com.june.daangnmarket.key.FirebaseVar.Companion.storage
import com.june.daangnmarket.model.ArticleModel
import com.june.daangnmarket.model.ChatListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat

class ArticleDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityArticleDetailBinding.inflate(layoutInflater) }
    lateinit var articleModel: ArticleModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val model = intent.getSerializableExtra("model")
        articleModel = model as ArticleModel

        initViews()
        initDateTextView()
        initImageView()
        initModifyButton()
        initChattingButton()
    }

    private fun initChattingButton() {
        //TODO 중복변수
        val productSellerId = articleModel.sellerId
        val productBuyerId: String? = auth?.currentUser?.uid

        if (productSellerId == productBuyerId || productBuyerId == null) {
            binding.chattingButton.visibility = View.INVISIBLE
        }

        binding.chattingButton.setOnClickListener {
            val chatRoom = ChatListModel(
                buyerId = productBuyerId!!,
                sellerId = productSellerId!!,
                itemTitle = articleModel.title!!,
                key = "${productBuyerId}_${productSellerId}",
                imageUrl = articleModel.imageUrl!!
            )

            //TODO DB 생성전에 이미 만들어진 DB가 있는지 확인할 필요가 있음

            val userDB = firebaseDBReference.child(DB_USERS)
            //구매자 DB
            userDB.child(productBuyerId)
                .child(CHILD_CHATROOM)
                .push()
                .setValue(chatRoom)
            //판매자 DB
            userDB.child(productSellerId)
                .child(CHILD_CHATROOM)
                .push()
                .setValue(chatRoom)
            Toast.makeText(this, "채팅방 생성", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initModifyButton() {
        //TODO 중복변수
        val articleWriterId = articleModel.sellerId
        val userId: String? = auth?.currentUser?.uid

        if (articleWriterId == userId) {
            binding.modifyButton.visibility = View.VISIBLE
            binding.modifyButton.setOnClickListener {  }
        }
    }

    private fun initDateTextView() {
        val createdAt = articleModel.createdAt
        val format = SimpleDateFormat("MM월 dd일")
        val date = Date(createdAt!!.toLong())
        binding.dateTextView.text = format.format(date).toString()
    }

    private fun initViews() {
        val price = articleModel.price
        val title = articleModel.title
        val description = articleModel.description
        val sellerId = articleModel.sellerId

        binding.idTextView.text = sellerId
        binding.priceTextView.text = price
        binding.descriptionTextView.text = description
        binding.titleTextView.text = title
    }

    private fun initImageView() {
        val imageUri = articleModel.imageUrl
        val storageReference = storage.reference
        val imgRef = storageReference.child("images_daangn/$imageUri.jpg")

        CoroutineScope(Dispatchers.IO).launch {
            imgRef.downloadUrl
                .addOnSuccessListener { uri ->


                    //TODO Solve context Issue
                    Glide.with(this@ArticleDetailActivity)
                        .load(uri)
                        .error(R.drawable.ic_baseline_cancel_24)
                        .into(binding.productImageView)

                    binding.progressBar.visibility = View.INVISIBLE
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@ArticleDetailActivity, "Error : $e", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "ArticleDetailActivity Error: $e")
                }
        }
    }


}