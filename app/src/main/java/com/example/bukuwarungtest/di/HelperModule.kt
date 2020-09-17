package com.example.bukuwarungtest.di

import com.example.bukuwarungtest.data.repositories.UserRepository
import com.example.bukuwarungtest.data.repositories.UserRepositoryImpl
import org.koin.dsl.module

val helperModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }
}