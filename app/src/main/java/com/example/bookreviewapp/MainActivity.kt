package com.example.bookreviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.bookreviewapp.api.InterParkBookInfo
import com.example.bookreviewapp.databinding.ActivityMainBinding
import com.example.bookreviewapp.model.BestSellerDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    companion object {
        private const val baseUrl = "https://book.interpark.com"
        private const val userAPIKey = ""
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bookService = retrofit.create(InterParkBookInfo::class.java)
        bookService.getBestSellerBooks(userAPIKey)
            .enqueue(object: Callback<BestSellerDTO> {
                override fun onResponse(call: Call<BestSellerDTO>, response: Response<BestSellerDTO>) {
                    if(response.isSuccessful.not()) { return }
                    //body 에 BestSellerDTO 형식의 데이터가 들어있음
                    response.body()?.let {
                        Log.d(TAG, it.toString())
                        it.bookDetail.forEach { a_book_info ->
                            Log.d(TAG, a_book_info.toString())
                        }
                    }
                }
                override fun onFailure(call: Call<BestSellerDTO>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "데이터 로드 실패", Toast.LENGTH_SHORT).show()
                }
            })
    }
}