package com.example.bukuwarungtest.di

import androidx.room.Room
import com.example.bukuwarungtest.data.dao.UserDao
import com.example.bukuwarungtest.data.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.Scope
import org.koin.dsl.module

val dataModule = module {

    single { provideDb() }

    single { provideUserDao(get()) }

}

private fun Scope.provideDb() =
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "BUKUWARUNG_TEST_DB").build()

fun provideUserDao(db: AppDatabase): UserDao {
    return db.getUserDao()
}

