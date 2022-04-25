package com.june.daangnmarket.mypage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.june.daangnmarket.R
import com.june.daangnmarket.databinding.FragmentMyPageBinding
import com.june.daangnmarket.share.FirebaseVar.Companion.auth
import com.june.daangnmarket.share.FirebaseVar.Companion.email

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
            activity?.finish()
        }
    }

    private fun initDeleteButton() {
        binding.deleteAccountButton.setOnClickListener {
            val mDialogView: View = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_delete, null)
            val closeButton: Button =  mDialogView.findViewById<Button>(R.id.closeButton)
            val deleteButton: Button = mDialogView.findViewById<Button>(R.id.deleteButton)

            val mDialogBuilder = AlertDialog.Builder(requireContext()).setView(mDialogView)
            val mAlertDialog = mDialogBuilder.show()

            closeButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
            deleteButton.setOnClickListener { view ->
                val user = auth.currentUser!!
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            email = null
                            Toast.makeText(requireContext(), "계정 삭제", Toast.LENGTH_SHORT)
                            activity?.finish()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "계정 삭제 실패", Toast.LENGTH_SHORT).show()
                    }
            }
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