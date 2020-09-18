package com.example.bukuwarungtest.views.userdetail

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bukuwarungtest.R
import com.example.bukuwarungtest.base.BaseActivity
import com.example.bukuwarungtest.data.model.User
import com.example.bukuwarungtest.views.UserViewModel
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class UserDetailActivity : BaseActivity() {

    private lateinit var user: User
    private var isEdit = false
    private lateinit var email: String
    private lateinit var fullname: String
    private val viewModel: UserViewModel by viewModel()

    override fun layoutRes() = R.layout.activity_user_detail

    override fun getToolbarTitle() = "Profile"

    override fun initView() {
        setupToolbar(toolbar)
        user = intent?.extras?.getParcelable("user") ?: User()
        setUserView(user)
    }

    private fun setUserView(user: User) {
        fullname = "${user.first_name} ${user.last_name}"
        email = "${user.email}"
        Glide.with(this)
            .load(user.avatar)
            .into(iv_avatar)
        tv_nama.text = fullname
        tv_email.text = email
        et_email.setText(email, TextView.BufferType.EDITABLE)
        et_nama.setText(fullname, TextView.BufferType.EDITABLE)
    }

}