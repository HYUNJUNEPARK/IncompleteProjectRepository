package com.june.daangnmarket.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.june.daangnmarket.databinding.FragmentMyPageBinding
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

        initCoverVisibility()
        binding.emailTextView.text = FirebaseVar.email
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initCoverVisibility() {
        if (FirebaseVar.auth.currentUser == null) {
            binding.noMemberCover.visibility = View.VISIBLE
        } else {
            binding.noMemberCover.visibility = View.INVISIBLE
        }
    }
}