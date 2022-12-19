package com.example.mydirectoryapp.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mydirectoryapp.R
import com.example.mydirectoryapp.adapter.ContactAdapter
import com.example.mydirectoryapp.adapter.FragmentAdapter
import com.example.mydirectoryapp.databinding.ActivityMainBinding
import com.example.mydirectoryapp.fragment.*
import com.example.mydirectoryapp.model.Contact
import com.example.mydirectoryapp.permission.Permission
import com.example.mydirectoryapp.util.DeviceInfo

class MainActivity: AppCompatActivity() {
    companion object {
        var contactListAll = mutableListOf<Contact>()
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var resultListener: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        //TODO 앱 처음 설치 시 권한 허가 창만 나오고 뷰가 안그려지는 문제 있음
        Permission(this).checkPermissions()

        initFragments()
        initLinkBottomNaviWithViewPager()
        initDrawerToggle()
        initDrawerNavigationItemListener()
        initResultListener()
    }

//Permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it ==  PackageManager.PERMISSION_GRANTED}) {
            Permission(this).permissionGranted()
        }
        else {
            Permission(this).permissionDenied()
        }
    }

//Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        menu?.apply {
            findItem(R.id.addPerson).isVisible = (binding.viewPager.currentItem == 0)
            findItem(R.id.filterRecentCalls).isVisible = (binding.viewPager.currentItem == 1)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Toolbar Menu Event
        when(item.itemId) {
            R.id.menu1 -> Toast.makeText(this, getString(R.string.test_message), Toast.LENGTH_SHORT).show()
            R.id.addPerson -> {
                var intent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI)
                resultListener.launch(intent)
            }
        }
        //DrawerLayout-NavigationView Event
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initResultListener() {
        resultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val adapter = binding.viewPager.adapter as FragmentAdapter
            val fragment = adapter.fragmentList[0] as ContactFragment
            val contactAdapter = fragment.binding.recyclerView.adapter as ContactAdapter
            contactListAll.clear()
            fragment.initDeviceContact()
            contactAdapter.contactList = contactListAll
            contactAdapter.notifyDataSetChanged()
        }
    }

//Fragment
    private fun initFragments() {
        val fragmentList = listOf(
            ContactFragment(),
            KeypadFragment(),
        )
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.viewPager.adapter = adapter
    }

//ViewPager2
    private fun initLinkBottomNaviWithViewPager() {
        val toolbarTitleList = listOf(
            getString(R.string.contact), getString(R.string.keypad)
        )
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigationContact -> {
                    binding.viewPager.currentItem = 0
                    binding.toolBarTitle.text = toolbarTitleList[0]
                    true
                }
                R.id.navigationKeypad -> {
                    binding.viewPager.currentItem = 2
                    binding.toolBarTitle.text = toolbarTitleList[1]
                    true
                }
                else -> {
                    false
                }
            }
        }
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //fragment 에 따라 메뉴를 바꿔주기 위해서 추가
                    //메뉴 전체를 무효화하여 다음번 메뉴를 열 때 onCreateOptionsMenu 가 다시 호출
                    invalidateOptionsMenu()

                    binding.toolBarTitle.text = toolbarTitleList[position]
                    binding.bottomNavigation.menu.getItem(position).isChecked = true
                }
            }
        )
    }

//Drawer
    private fun initDrawerToggle() {
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opend, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()
    }

    private fun initDrawerNavigationItemListener() {
        binding.mainDrawerView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.item1 -> Toast.makeText(this, "Item1 Clicked", Toast.LENGTH_SHORT).show()
                R.id.item2 -> Toast.makeText(this, "Item2 Clicked", Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(this, "Item3 Clicked", Toast.LENGTH_SHORT).show()
                R.id.item4 -> Toast.makeText(this, "Item4 Clicked", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}