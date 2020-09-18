package com.example.bukuwarungtest.di

import com.example.bukuwarungtest.data.repositories.UserRepository
import com.example.bukuwarungtest.data.repositories.UserRepositoryImpl
import com.example.bukuwarungtest.utils.PrefManager
import org.koin.dsl.module

val helperModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    single { PrefManager.getInstance() }
}