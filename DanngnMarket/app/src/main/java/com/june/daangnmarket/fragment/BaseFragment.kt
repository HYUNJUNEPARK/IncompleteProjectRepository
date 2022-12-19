package com.june.daangnmarket.fragment

import android.content.Context
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.june.daangnmarket.activity.SignInActivity
import com.june.daangnmarket.dialog.CloseMainActivityDialog
import com.june.daangnmarket.key.FirebaseVar.Companion.auth

abstract class BaseFragment: Fragment() {
    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val userId:String? = auth?.currentUser?.uid

                if (userId == null) { //비회원
                    activity?.finish()
                    val intent = Intent(activity, SignInActivity::class.java)
                    requireContext().startActivity(intent)
                }
                else { //회원
                    val mDialog = CloseMainActivityDialog()
                    mDialog.closeMainActivityDialog(requireActivity())
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}