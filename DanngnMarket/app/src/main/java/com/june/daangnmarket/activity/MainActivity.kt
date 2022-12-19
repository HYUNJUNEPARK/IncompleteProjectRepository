package com.june.daangnmarket.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.june.daangnmarket.R
import com.june.daangnmarket.fragment.ChatListFragment
import com.june.daangnmarket.databinding.ActivityMainBinding
import com.june.daangnmarket.fragment.HomeFragment
import com.june.daangnmarket.fragment.MyPageFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
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
        replaceFragment(fragmentList[0])
        initFragment()
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