package com.june.daangnmarket.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.june.daangnmarket.DBKey.Companion.DB_ARTICLES
import com.june.daangnmarket.DBKey.Companion.TAG
import com.june.daangnmarket.databinding.FragmentHomeBinding
import android.content.Intent
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var articleAdapter: ArticleAdapter
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private lateinit var articleDB: DatabaseReference
    private val articleList = mutableListOf<ArticleModel>()


    private val listener2 = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            //TODO 다큐먼트 값이 추가 됨
            articleList.clear()


            var testList = mutableListOf<String>()

            for (article in snapshot.children) {
                Log.d(TAG, "onDataChange: ${article.value}")
//                article.value
//                testList.add(article.value.toString())
            }
            //Log.d(TAG, "onDataChange: $testList")
//            for(i in testList) {
//
//            }

//            val articleModel = ArticleModel(
//                /*createAt*/testList[0].toLong(),
//                /*imageUrl*/testList[1],
//                /*price*/testList[2],
//                /*sellerId*/testList[3],
//                /*title*/testList[4]
//            )
//            articleList.add(articleModel)
//            articleAdapter.submitList(articleList)
        }

        override fun onCancelled(error: DatabaseError) {}
    }

//    private val listener = object : ChildEventListener {
//        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//            //Log.d(TAG, "onChildAdded: ${snapshot.key}")
//            val articleModel = snapshot.getValue(ArticleModel::class.java)
//            articleModel ?: return
//            articleList.add(articleModel)
//            articleAdapter.submitList(articleList)
//        }
//        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
//        override fun onChildRemoved(snapshot: DataSnapshot) {}
//        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
//        override fun onCancelled(error: DatabaseError) {}
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        binding.addFloatingButton.setOnClickListener {
            //TODO 로그인 기능 구현 할 것
            //if (auth.currentUser != null) {
                val intent = Intent(requireContext(), AddArticleActivity::class.java)
                startActivity(intent)
//            } else {
//                Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
//
//            }
        }
    }

    private fun initRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articleRecyclerView.adapter = articleAdapter
        articleDB = Firebase.database.reference.child(DB_ARTICLES)

        //articleDB.addChildEventListener(listener)
        articleDB.addValueEventListener(listener2)
    }

    override fun onResume() {
        super.onResume()
        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        //articleDB.removeEventListener(listener)
        articleDB.removeEventListener(listener2)
    }
}