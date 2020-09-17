package com.example.bukuwarungtest.views.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bukuwarungtest.R
import com.example.bukuwarungtest.base.BaseFragment
import com.example.bukuwarungtest.views.home.HomeFragment

class ProfileFragment : BaseFragment() {

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

    }

    override fun observeData() {

    }
}