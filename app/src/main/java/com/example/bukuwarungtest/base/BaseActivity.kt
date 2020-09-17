package com.example.bukuwarungtest.base

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutRes(): Int

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        initView()
    }

    fun setupToolbar(toolbar: Toolbar) {
        if (getToolbarTitle() != null) {
            toolbar.title = getToolbarTitle()
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    open fun getToolbarTitle(): String? = null

}