package com.example.bookreviewapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.bookreviewapp.databinding.ActivityDetailBinding
import com.example.bookreviewapp.db.AppDataBase
import com.example.bookreviewapp.db.getAppDatabase
import com.example.bookreviewapp.model.BookDetailDto
import com.example.bookreviewapp.model.Review

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        db = Room.databaseBuilder(
//            applicationContext,
//            AppDataBase::class.java,
//            "BookSearchDB"
//        ).build()

        db = getAppDatabase(this)

        val model = intent.getParcelableExtra<BookDetailDto>("bookModel")

        binding.titleTextView.text = model?.title.orEmpty()
        binding.descriptionTextView.text = model?.description.orEmpty()

        //이 글라이드에서는 this 를 써서 context 를 넘겨줄 수 있음
        //이유는 DetailActivity 가 AppCompatActivity 를 상속 받았기 때문
        Glide.with(binding.coverImageView)
             .load(model?.coverSmallUrl.orEmpty())
             .into(binding.coverImageView)

        Thread {
            val review = db.reviewDao().getOneReview(model?.id?.toInt() ?:0 )
            runOnUiThread {
                binding.reviewEditText.setText(review?.review.orEmpty())
            }
        }.start()

        binding.saveButton.setOnClickListener {
            Thread {
                db.reviewDao().saveReview(
                    Review(
                        model?.id?.toInt() ?: 0,
                        binding.reviewEditText.text.toString()
                    )
                )
            }.start()
        }
    }
}