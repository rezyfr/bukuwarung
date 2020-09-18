package com.example.bukuwarungtest.data.repositories

import com.example.bukuwarungtest.data.api.ReqresApi
import com.example.bukuwarungtest.data.dao.UserDao
import com.example.bukuwarungtest.data.model.User
import com.example.bukuwarungtest.data.model.UserModel
import retrofit2.Response

interface UserRepository {
    suspend fun fetchUsers(): Response<UserModel>
    suspend fun getUserList(): List<User>
    suspend fun getUserById(id: Int): User
    suspend fun saveUsers(userList: List<User>)
    suspend fun updateUser(user: User)
}

class UserRepositoryImpl(
        private val reqresApi: ReqresApi,
        private val userDao: UserDao
) : UserRepository {

    override suspend fun fetchUsers(): Response<UserModel> {
        return reqresApi.fetchUser()
    }

    override suspend fun getUserList(): List<User> {
        return userDao.getUserList()
    }

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id)
    }

    override suspend fun saveUsers(userList: List<User>) {
        return userDao.insertAll(userList)
    }

    override suspend fun updateUser(user: User) {
        return userDao.update(user)
    }
}