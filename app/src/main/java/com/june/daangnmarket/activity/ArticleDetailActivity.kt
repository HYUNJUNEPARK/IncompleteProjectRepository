package com.june.daangnmarket.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.june.daangnmarket.R
import com.june.daangnmarket.databinding.ActivityArticleDetailBinding
import com.june.daangnmarket.key.DBKey.Companion.ARTICLE_MODEL_INTENT
import com.june.daangnmarket.key.DBKey.Companion.CHILD_CHATROOM
import com.june.daangnmarket.key.DBKey.Companion.DB_USERS
import com.june.daangnmarket.key.DBKey.Companion.SELLER_ID
import com.june.daangnmarket.key.DBKey.Companion.TAG
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.firebaseDBReference
import com.june.daangnmarket.key.FirebaseVar.Companion.storage
import com.june.daangnmarket.model.ArticleModel
import com.june.daangnmarket.model.ChatListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.sql.Date
import java.text.SimpleDateFormat

class ArticleDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityArticleDetailBinding.inflate(layoutInflater) }
    lateinit var articleModel: ArticleModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val model = intent.getSerializableExtra(ARTICLE_MODEL_INTENT)
        articleModel = model as ArticleModel
        val mGlideRequestManager: RequestManager = Glide.with(this)
        initViews()
        initDateTextView()
        initImageView(mGlideRequestManager)
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
        else {
            binding.chattingButton.setOnClickListener {
                val chatRoom = ChatListModel(
                    buyerId = productBuyerId!!,
                    sellerId = productSellerId!!,
                    itemTitle = articleModel.title!!,
                    key = "${productBuyerId}_${productSellerId}_${articleModel.imageUrl!!}",
                    imageUrl = articleModel.imageUrl!!,
                    createAt = System.currentTimeMillis()
                )

                //TODO DB 생성전에 이미 만들어진 DB가 있는지 확인할 필요가 있음
                val userDB = firebaseDBReference.child(DB_USERS)
                //[START ]
                //키값 도달!! 성공 사례 -> setValue 과정에서 push() 를 뺐음
                val key = userDB.child(productBuyerId).child("DaangnChatRoom").child("TODO ID").child("key")
                Log.d(TAG, "articleDetailActivity: ${key.get()}")
                key.get().addOnSuccessListener {
                    Log.d(TAG, "data: ${it.value}")
                }
                //data: dkox9OMpmkZtLQzswAjFlIKeEfF2_5q6bMy7u2KcW3Fu4l4N0E9UCGQU2_5q6bMy7u2KcW3Fu4l4N0E9UCGQU21651048631218
                //[END ]

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

    private fun initImageView(mGlideRequestManager: RequestManager) {
        val mGlideRequestManager = mGlideRequestManager

        val imageUri = articleModel.imageUrl
        val storageReference = storage.reference
        val imgRef = storageReference.child("images_daangn/$imageUri.jpg")

        CoroutineScope(Dispatchers.IO).launch {
            imgRef.downloadUrl
                .addOnSuccessListener { uri ->
                    mGlideRequestManager
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