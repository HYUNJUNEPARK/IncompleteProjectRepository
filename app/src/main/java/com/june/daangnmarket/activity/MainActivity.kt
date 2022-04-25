package com.june.daangnmarket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.june.daangnmarket.R
import com.june.daangnmarket.chatlist.ChatListFragment
import com.june.daangnmarket.databinding.ActivityMainBinding
import com.june.daangnmarket.home.HomeFragment
import com.june.daangnmarket.mypage.MyPageFragment
import com.june.daangnmarket.network.NetworkConnection

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val networkCheck: NetworkConnection by lazy {
        NetworkConnection(this)
    }
    companion object {
        val fragmentList = arrayListOf (
            HomeFragment(),
            ChatListFragment(),
            MyPageFragment()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        networkCheck.register()
        replaceFragment(fragmentList[0])
        initFragment()
    }

    override fun onDestroy() {
        super.onDestroy()

        networkCheck.unregister()
    }

    private fun initFragment(){
        binding.bottomNavigationView.setOnItemSelectedListener{ menu ->
            when (menu.itemId) {
                R.id.home -> replaceFragment(fragmentList[0])
                R.id.chatList -> replaceFragment(fragmentList[1])
                R.id.myPage -> replaceFragment(fragmentList[2])
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }
}