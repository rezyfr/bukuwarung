package com.example.bukuwarungtest.di

import com.example.bukuwarungtest.BuildConfig
import com.example.bukuwarungtest.data.api.ReqresApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkHttpClient() }

    single { provideBaseRetrofit(get()) }

    factory { provideReqresApi(get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient().newBuilder()
    builder.addInterceptor(provideLoggingInterceptor())
            .retryOnConnectionFailure(true)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)

    return builder.build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return interceptor
}

fun provideGsonConverter(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(provideGsonConverter()).build()
}

fun provideReqresApi(retrofit: Retrofit): ReqresApi =
        retrofit.create(ReqresApi::class.java)