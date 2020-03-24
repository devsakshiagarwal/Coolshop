package com.sakshi.coolshopapplication.arch

import com.sakshi.coolshopapplication.CoolShop
import com.sakshi.coolshopapplication.model.apis.LoginApi
import com.sakshi.coolshopapplication.model.apis.UserInfoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CompRoot(val coolShop: CoolShop) {

    private lateinit var retrofit: Retrofit
    private lateinit var client: OkHttpClient

    init {
        initHttpClient()
        initRetrofit()
    }

    //OkHttpClient for retrofit
    private fun initHttpClient() {
        //logging interceptor for retrofit client
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        client =
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Urls.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun getRetrofit() = retrofit


    fun getLoginApi(): LoginApi = getRetrofit().create(LoginApi::class.java)

    fun getUserInfoApi(): UserInfoApi = getRetrofit().create(UserInfoApi::class.java)
}