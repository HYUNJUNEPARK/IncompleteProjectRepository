package com.june.daangnmarket.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.june.daangnmarket.R
import com.june.daangnmarket.databinding.FragmentMyPageBinding
import com.june.daangnmarket.share.DBKey.Companion.TAG
import com.june.daangnmarket.share.FirebaseVar

class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailTextView.text = FirebaseVar.email
        Log.d(TAG, "onViewCreated: ${FirebaseVar.email}")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}