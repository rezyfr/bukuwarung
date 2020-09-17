package com.example.bukuwarungtest.views.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.bukuwarungtest.R
import com.example.bukuwarungtest.base.BaseFragment
import com.example.bukuwarungtest.data.model.User
import com.example.bukuwarungtest.views.UserViewModel
import com.example.bukuwarungtest.views.home.adapter.UserAdapter
import com.example.bukuwarungtest.views.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var userAdapter: UserAdapter
    private var isLoading = false
    private val viewModel: UserViewModel by viewModel()
    private var userList = listOf<User>()

    override fun layoutRes() = R.layout.fragment_home

    override fun initView() {
        userAdapter = UserAdapter(arrayListOf()) {
            onUserClicked(it)
        }
        rvUser.adapter = userAdapter
        lifecycleScope.launch {
            userList = viewModel.getUserList()
            if (userList.isEmpty()) {
                viewModel.fetchUser()
            } else {
                userAdapter.setData(userList)
            }
        }
    }

    override fun observeData() {
        viewModel.stateFetchUser.observe(viewLifecycleOwner, Observer {
            when (it) {
                is UserViewModel.StateFetchUser.Loading -> {
                    isLoading = true
                    stateLoading(isLoading)
                }
                is UserViewModel.StateFetchUser.Success -> {
                    val params = progress_circular.layoutParams as RelativeLayout.LayoutParams
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    progress_circular.layoutParams = params
                    isLoading = false
                    stateLoading(isLoading)
                    lifecycleScope.launch {
                        userAdapter.setData(viewModel.getUserList())
                    }
                }
                is UserViewModel.StateFetchUser.Error -> {
                    isLoading = false
                    stateLoading(isLoading)
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun onUserClicked(user: User) {
        val intent = Intent(activity, UserDetailActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    private fun stateLoading(isLoading: Boolean) {
        if (isLoading) {
            progress_circular.visibility = View.VISIBLE
        } else {
            progress_circular.visibility = View.GONE
        }
    }
}