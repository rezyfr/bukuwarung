package com.example.bukuwarungtest.views

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.example.bukuwarungtest.R
import com.example.bukuwarungtest.base.BaseActivity
import com.example.bukuwarungtest.views.home.HomeFragment
import com.example.bukuwarungtest.views.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun layoutRes() = R.layout.activity_main

    override fun initView() {
        bottom_nav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    val fragment = HomeFragment.newInstance()
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_profile -> {
                    val fragment = ProfileFragment.newInstance()
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        val fragment = HomeFragment.newInstance()
        addFragment(fragment)
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}