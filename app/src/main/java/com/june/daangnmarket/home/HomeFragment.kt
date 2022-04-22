package com.june.daangnmarket.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.june.daangnmarket.databinding.FragmentHomeBinding
import com.june.daangnmarket.share.DBKey.Companion.CREATED_AT
import com.june.daangnmarket.share.DBKey.Companion.DB_ARTICLES
import com.june.daangnmarket.share.DBKey.Companion.IMAGE_URL
import com.june.daangnmarket.share.DBKey.Companion.PRICE
import com.june.daangnmarket.share.DBKey.Companion.SELLER_ID
import com.june.daangnmarket.share.DBKey.Companion.TITLE
import com.june.daangnmarket.share.FirebaseVar

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleDB: DatabaseReference
    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            articleList.clear()

             for (article in snapshot.children.reversed()) {
                //{createdAt=1650543, sellerId=, price=988745, imageUrl=, title=test}
                val articleMap = article.value as HashMap<String, String>
                val createdAt = articleMap[CREATED_AT].toString()
                val imageUrl = articleMap[IMAGE_URL]
                val price = articleMap[PRICE]
                val sellerId = articleMap[SELLER_ID]
                val title = articleMap[TITLE]
                
                val articleModel = ArticleModel(
                    createdAt!!.toLong(),
                    imageUrl,
                    price,
                    sellerId,
                    title
                )
                articleList.add(articleModel)
                articleAdapter.submitList(articleList)

                binding.progressBar.visibility = View.INVISIBLE
            }
        }
        override fun onCancelled(error: DatabaseError) {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE

        initRecyclerView()
        setVisibilityFloatingButton()

        binding.addFloatingButton.setOnClickListener {
                val intent = Intent(requireContext(), AddArticleActivity::class.java)
                startActivity(intent)
//
        }
    }

    override fun onResume() {
        super.onResume()
        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        articleDB.removeEventListener(listener)
    }

    private fun initRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articleRecyclerView.adapter = articleAdapter
        articleDB = Firebase.database.reference.child(DB_ARTICLES)
        articleDB.addValueEventListener(listener)
    }

    private fun setVisibilityFloatingButton() {
        if (FirebaseVar.auth.currentUser != null) {
            binding.addFloatingButton.visibility = View.VISIBLE
        } else {
            binding.addFloatingButton.visibility = View.INVISIBLE
        }
    }
}