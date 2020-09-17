package com.example.bukuwarungtest.views.userdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bukuwarungtest.R
import com.example.bukuwarungtest.base.BaseActivity
import com.example.bukuwarungtest.data.model.User
import kotlinx.android.synthetic.main.activity_profile.*

class UserDetailActivity : BaseActivity() {

    private lateinit var user: User

    override fun layoutRes() = R.layout.activity_profile

    override fun getToolbarTitle() = "Profile"

    override fun initView() {
        setupToolbar(toolbar)
        user = intent?.extras?.getParcelable("user") ?: User()
    }
}