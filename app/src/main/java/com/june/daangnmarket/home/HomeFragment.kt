package com.june.daangnmarket.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.june.daangnmarket.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
        articleAdapter = ArticleAdapter()

        //sample data
        articleAdapter.submitList(mutableListOf<ArticleModel>().apply {
            add(ArticleModel("0", "aaa", 1000000, "5000", ""))
            add(ArticleModel("0", "aaa", 2000000, "59000", ""))
            add(ArticleModel("0", "aaa", 1000000, "5000", ""))
            add(ArticleModel("0", "aaa", 2000000, "59000", ""))
            add(ArticleModel("0", "aaa", 1000000, "5000", ""))
            add(ArticleModel("0", "aaa", 2000000, "59000", ""))
            add(ArticleModel("0", "aaa", 1000000, "5000", ""))
            add(ArticleModel("0", "aaa", 2000000, "59000", ""))
        })

        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articleRecyclerView.adapter = articleAdapter
    }
}