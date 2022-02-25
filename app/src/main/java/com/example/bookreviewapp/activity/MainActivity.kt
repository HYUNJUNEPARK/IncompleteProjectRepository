package com.example.bookreviewapp.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.bookreviewapp.R
import com.example.bookreviewapp.adapter.BookAdapter
import com.example.bookreviewapp.adapter.HistoryAdapter
import com.example.bookreviewapp.api.InterParkAPI
import com.example.bookreviewapp.databinding.ActivityMainBinding
import com.example.bookreviewapp.db.AppDataBase
import com.example.bookreviewapp.model.BestSellerDto
import com.example.bookreviewapp.model.History
import com.example.bookreviewapp.model.SearchBookDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    private lateinit var adapter: BookAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var bookService: InterParkAPI
    private lateinit var db: AppDataBase

    companion object {
        private const val baseUrl = "https://book.interpark.com"
        private const val TAG = "CheckLog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initBookRecyclerView()
        initHistoryRecyclerView()

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "BookSearchDB"
        ).build()


        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        bookService = retrofit.create(InterParkAPI::class.java)

        bookService.getBestSellerBooks(getString(R.string.interParkApiKey))
            .enqueue(object: Callback<BestSellerDto> {
                override fun onResponse(call: Call<BestSellerDto>, response: Response<BestSellerDto>) {
                    if(response.isSuccessful.not()) { return }
                    response.body()?.let {

//                        Log.d(TAG, it.toString())
//                        it.bookDetail.forEach { a_book_info ->
//                            Log.d(TAG, a_book_info.toString())
//                        }

                        adapter.submitList(it.bookDetail)//리스트가 서버에서 가져온 리스트로 변경됨
                    }
                }
                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    Log.e(TAG, "[onFailure] : $t")
                    Toast.makeText(this@MainActivity, "데이터 로드 실패", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun initHistoryRecyclerView() {
        historyAdapter = HistoryAdapter(historyDeleteClickedListener = { deleteSearchKeyword(it) })
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = historyAdapter
        initSearchEditText()
    }

    private fun deleteSearchKeyword(keyword: String) {
        Thread {
            db.historyDAO().delete(keyword)
            showHistoryView()
        }.start()
    }

    private fun initBookRecyclerView() {
        adapter = BookAdapter(itemClickedListener = {
            val intent = Intent(this, DetailActivity::class.java)

            //BookDetailDto 의 변수를 하나씩 넘길수 있지만 intent.putExtra("aaa",it.description)
            //직렬화하면 클래스 전체를 넘길 수 있음
            intent.putExtra("bookModel", it)

            startActivity(intent)
        })
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter
    }

    private fun search(keyword: String) {
        bookService.getBooksByName(getString(R.string.interParkApiKey), keyword)
            .enqueue(object: Callback<SearchBookDto> {
                override fun onResponse(call: Call<SearchBookDto>, response: Response<SearchBookDto>) {
                    hideHistoryView()
                    saveSearchKeyword(keyword)

                    if( response.isSuccessful.not() ) { return }
                    response.body()?.let {
//                        Log.d(TAG, it.toString())
//                        it.bookDetails.forEach { a_book_info ->
//                            Log.d(TAG, a_book_info.toString())
//                        }
                        adapter.submitList(response.body()?.bookDetails.orEmpty())//리스트가 서버에서 가져온 리스트로 변경됨
                    }
                }
                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    hideHistoryView()
                    Log.e(TAG, "[onFailure] : $t")
                    Toast.makeText(this@MainActivity, "데이터 로드 실패", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showHistoryView() {
        Thread {
            val keywords = db.historyDAO().getAll().reversed() //최신 순으로
            runOnUiThread {
                binding.historyRecyclerView.isVisible = true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
        binding.historyRecyclerView.isVisible = true
    }

    private fun hideHistoryView() {
        binding.historyRecyclerView.isVisible = false
    }

    private fun saveSearchKeyword(keyword: String) {
        Thread {
            db.historyDAO().insertHist(History(null, keyword))
        }.start()
    }

    private fun initSearchEditText() {
        binding.searchEditText.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == MotionEvent.ACTION_DOWN){
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            else {
                return@setOnKeyListener false
            }
        }
        binding.searchEditText.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
            }
            return@setOnTouchListener false
        }
    }
}