package com.example.bukuwarungtest.di

import com.example.bukuwarungtest.views.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { UserViewModel(get(), get()) }
}