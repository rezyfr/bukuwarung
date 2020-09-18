package com.example.bukuwarungtest.views.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bukuwarungtest.R
import com.example.bukuwarungtest.base.BaseFragment
import com.example.bukuwarungtest.data.model.User
import com.example.bukuwarungtest.views.UserViewModel
import com.example.bukuwarungtest.views.home.HomeFragment
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.et_email
import kotlinx.android.synthetic.main.fragment_profile.et_nama
import kotlinx.android.synthetic.main.fragment_profile.iv_avatar
import kotlinx.android.synthetic.main.fragment_profile.tv_email
import kotlinx.android.synthetic.main.fragment_profile.tv_nama
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private var user = User()
    private var isEdit = false
    private lateinit var email: String
    private lateinit var fullname: String
    private val viewModel: UserViewModel by viewModel()

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutRes() = R.layout.fragment_profile

    override fun initView() {
        tv_edit.setOnClickListener {
            if (!isEdit) {
                isEdit = true
                tv_nama.visibility = View.GONE
                tv_email.visibility = View.GONE
                tv_edit.visibility = View.GONE
                et_email.visibility = View.VISIBLE
                et_nama.visibility = View.VISIBLE
                btn_confirm.visibility = View.VISIBLE
            }
        }
        btn_confirm.setOnClickListener {
            if (isEdit) {
                isEdit = false
                if (et_email.text.isNotEmpty() || et_nama.text.isNotEmpty()) {
                    val newEmail = et_email.text.toString()
                    val fullname = "${et_nama.text}"
                    lifecycleScope.launch {
                        viewModel.updateUser(
                            newEmail, fullname, user.id ?: 0)
                    }
                }
                tv_nama.visibility = View.VISIBLE
                tv_email.visibility = View.VISIBLE
                tv_edit.visibility = View.VISIBLE
                et_email.visibility = View.GONE
                et_nama.visibility = View.GONE
                btn_confirm.visibility = View.GONE
            }
        }
    }

    override fun observeData() {
        viewModel.stateUpdateUser.observe(this, Observer {
            when (it) {
                is UserViewModel.StateUpdateUser.Success -> {
                    setUserView(it.user)
                }
                is UserViewModel.StateUpdateUser.Error -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun setUserView(user: User) {
        fullname = "${user.first_name} ${user.last_name}"
        email = "${user.email}"
        tv_nama.text = fullname
        tv_email.text = email
        et_email.setText(email, TextView.BufferType.EDITABLE)
        et_nama.setText(fullname, TextView.BufferType.EDITABLE)
    }

}