package com.june.daangnmarket.home

import android.R.attr.logoDescription
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.june.daangnmarket.DBKey.Companion.DB_ARTICLES
import com.june.daangnmarket.DBKey.Companion.TAG
import com.june.daangnmarket.databinding.FragmentHomeBinding
import java.util.HashMap

import android.R.attr.name
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlin.math.log


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var articleAdapter: ArticleAdapter
//    private val auth: FirebaseAuth by lazy {
//        Firebase.auth
//    }
    private lateinit var articleDB: DatabaseReference
    private val articleList = mutableListOf<ArticleModel>()


    private val listener2 = object  : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            //var snapshotMap = mutableMapOf<String?, Any?>()

            var testList = mutableListOf<String>()
            
            for (i in snapshot.children) {
                testList.add(i.value.toString())
                //snapshotMap.put(i.key, i.value)
            }

            val articleModel = ArticleModel(
                testList[0].toLong(),
                testList[1],
                testList[2],
                testList[3],
                testList[4]
            )
            Log.d(TAG, "onDataChange: $articleModel")
            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
            testList.clear()

//            Log.d(TAG, "onDataChange: ${snapshotMap["createdAt"]}")
//            val articleModel = ArticleModel(
//                snapshotMap["createdAt"],
//                snapshotMap["imageUrl"],
//                snapshotMap["price"],
//                snapshotMap["sellerId"],
//                snapshotMap["title"]
//            )
//            Log.d(TAG, "onDataChange: $articleModel")
//            articleList.add(articleModel)
//
//            for(ds in snapshot.children) {
//
//                val articleModel = ds.getValue(ArticleModel::class.java)
//                Log.d(TAG, "onDataChange: $articleModel")
//
//            }
        }

        override fun onCancelled(error: DatabaseError) { }
    }


    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            //Log.d(TAG, "onChildAdded: ${snapshot.key}")

            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return


            snapshot.children.forEach { it ->


            }

            for( i in snapshot.children) {

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
        Log.d(TAG, "onDestroy: ")
        articleDB.removeEventListener(listener2)
    }
}