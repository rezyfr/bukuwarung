package com.example.bukuwarungtest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bukuwarungtest.data.model.User
import com.example.bukuwarungtest.data.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE

            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, "BUKUWARUNG_TEST_DB").allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun getUserDao(): UserDao
}