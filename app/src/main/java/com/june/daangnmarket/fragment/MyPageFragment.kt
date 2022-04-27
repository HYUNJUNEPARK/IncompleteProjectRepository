package com.june.daangnmarket.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.june.daangnmarket.activity.SignInActivity
import com.june.daangnmarket.databinding.FragmentMyPageBinding
import com.june.daangnmarket.dialog.DeleteAccountDialog
import com.june.daangnmarket.key.FirebaseVar.Companion.auth
import com.june.daangnmarket.key.FirebaseVar.Companion.email

class MyPageFragment : BaseFragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emailTextView.text = email
        initNoMemberCover()
        initCoverVisibility()
        initDeleteButton()
        initSignOutButton()
    }

    private fun initNoMemberCover() {
        binding.noMemberCover.setOnTouchListener { view, motionEvent ->
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initSignOutButton() {
        binding.signOutButton.setOnClickListener {
            auth.signOut()
            email = null
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun initDeleteButton() {
        binding.deleteAccountButton.setOnClickListener {
            val myDialog = DeleteAccountDialog()
            myDialog.deleteAccountDialog(requireActivity(), requireContext())
        }
    }

    private fun initCoverVisibility() {
        if (auth.currentUser == null) {
            binding.noMemberCover.visibility = View.VISIBLE
        } else {
            binding.noMemberCover.visibility = View.INVISIBLE
        }
    }
}