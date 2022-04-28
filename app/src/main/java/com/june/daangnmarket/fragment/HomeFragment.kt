package com.june.daangnmarket.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.june.daangnmarket.activity.AddArticleActivity
import com.june.daangnmarket.adapter.HomeAdapter
import com.june.daangnmarket.databinding.FragmentHomeBinding
import com.june.daangnmarket.key.DBKey.Companion.CREATED_AT
import com.june.daangnmarket.key.DBKey.Companion.DB_ARTICLES
import com.june.daangnmarket.key.DBKey.Companion.DESCRIPTION
import com.june.daangnmarket.key.DBKey.Companion.IMAGE_URL
import com.june.daangnmarket.key.DBKey.Companion.PRICE
import com.june.daangnmarket.key.DBKey.Companion.SELLER_ID
import com.june.daangnmarket.key.DBKey.Companion.TITLE
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.firebaseDBReference
import com.june.daangnmarket.model.ArticleModel
import com.june.daangnmarket.model.ChatListItemModel
import com.june.daangnmarket.model.ChatListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var articleDB: DatabaseReference
    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            articleList.clear()
            if (snapshot.value == null) {
                binding.progressBar.visibility = View.INVISIBLE
                return
            }
             for (article in snapshot.children.reversed()) {
                //{createdAt=1650543, sellerId=, price=988745, imageUrl=, title=test}
                val articleMap = article.value as HashMap<String, String>
                val createdAt = articleMap[CREATED_AT].toString()
                val imageUrl = articleMap[IMAGE_URL]
                val price = articleMap[PRICE]
                val title = articleMap[TITLE]
                val sellerId = articleMap[SELLER_ID]
                val description = articleMap[DESCRIPTION]

                val articleModel = ArticleModel(
                    createdAt!!.toLong(),
                    imageUrl,
                    price,
                    sellerId,
                    title,
                    description
                )
                articleList.add(articleModel)
                homeAdapter.submitList(articleList)
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
        val mGlideRequestManager = Glide.with(this)
        initRecyclerView(mGlideRequestManager)
        visibilityFloatingButton()
        binding.addFloatingButton.setOnClickListener {
            val intent = Intent(requireContext(), AddArticleActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        homeAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        articleDB.removeEventListener(listener)
    }

    private fun initRecyclerView(mGlideRequestManager: RequestManager) {
        val mGlideRequestManager = mGlideRequestManager
        homeAdapter = HomeAdapter(mGlideRequestManager)
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articleRecyclerView.adapter = homeAdapter
        articleDB = firebaseDBReference.child(DB_ARTICLES)

        CoroutineScope(Dispatchers.Default).launch {
            articleDB.addValueEventListener(listener)
        }
    }

    private fun visibilityFloatingButton() {
        if (auth?.currentUser != null) {
            binding.addFloatingButton.visibility = View.VISIBLE
        } else {
            binding.addFloatingButton.visibility = View.INVISIBLE
        }
    }
}