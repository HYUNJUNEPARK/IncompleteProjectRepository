package com.june.daangnmarket.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.june.daangnmarket.DBKey.Companion.DB_ARTICLES
import com.june.daangnmarket.databinding.FragmentHomeBinding

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

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val articleModel = snapshot.getValue(ArticleModel::class.java)


            Log.d("testLog", "onChildAdded: $articleModel")
            //articleModel ?: return

            if (articleModel == null) {
                Log.d("testLog", "onChildAdded: kkkkkkkkkkkkkkkkkkkkk")
                return
            }

            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }
        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
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
        Log.d("testLog", "onChildAdded: bbbbbbbbbbbb")
        articleAdapter = ArticleAdapter()

        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articleRecyclerView.adapter = articleAdapter
        articleDB = Firebase.database.reference.child(DB_ARTICLES)


        val listener2 = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val articleModel = snapshot.getValue(ArticleModel::class.java)


                Log.d("testLog", "onChildAdded: $articleModel")
                //articleModel ?: return

                if (articleModel == null) {
                    Log.d("testLog", "onChildAdded: kkkkkkkkkkkkkkkkkkkkk")
                    return
                }else {
                    Log.d("testLog", "onChildAdded: afdasfasdfasdfdasfasdk")
                }

                articleList.add(articleModel)
                articleAdapter.submitList(articleList)
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        }


        articleDB.addChildEventListener(listener2)
        Log.d("testLog", "onChildAdded:$articleDB")
    }
}