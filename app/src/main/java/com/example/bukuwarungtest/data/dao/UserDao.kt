package com.example.bukuwarungtest.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.bukuwarungtest.base.BaseDao
import com.example.bukuwarungtest.data.model.User

@Dao
interface UserDao : BaseDao<User> {
    @Query("SELECT * FROM `User` ORDER BY first_name ASC")
    suspend fun getUserList(): List<User>
}