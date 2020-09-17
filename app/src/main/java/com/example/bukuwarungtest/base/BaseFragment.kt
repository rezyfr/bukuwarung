package com.example.bukuwarungtest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    @LayoutRes
    protected abstract fun layoutRes(): Int

    @StringRes
    protected open fun titleRes(): Int? = null

    abstract fun initView()

    abstract fun observeData()

    open fun initData() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(layoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        observeData()
    }
}
