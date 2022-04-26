package com.june.daangnmarket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.june.daangnmarket.databinding.ActivityArticleDetailBinding
import com.june.daangnmarket.home.ArticleModel
import com.june.daangnmarket.share.DBKey.Companion.CREATED_AT
import com.june.daangnmarket.share.DBKey.Companion.TAG

class ArticleDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityArticleDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val model = intent.getSerializableExtra("model")
        //ArticleModel(createdAt=1650943436309, imageUri=e2GLIulvwCRWw9mwUhzHrvOwVQj21650943436309, price=4000, sellerId=e2GLIulvwCRWw9mwUhzHrvOwVQj2, title=애플워치 루프, description=다른 버전 호환 가능)

        val articleModel = model as ArticleModel
        val createdAt = articleModel.createdAt

        binding.sellerIdTextView.text = createdAt.toString()

        Log.d(TAG, "model: $model")
        Log.d(TAG, "onCreate: $createdAt")


        
    }
}