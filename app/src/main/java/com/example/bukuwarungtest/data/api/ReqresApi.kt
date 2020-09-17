package com.example.bukuwarungtest.data.api

import com.example.bukuwarungtest.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresApi {
    @GET("api/users")
    suspend fun getCryptoList(
    ): Response<UserModel>
}