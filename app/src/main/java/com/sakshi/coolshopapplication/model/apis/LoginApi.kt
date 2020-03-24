package com.sakshi.coolshopapplication.model.apis

import com.sakshi.coolshopapplication.arch.Urls
import com.sakshi.coolshopapplication.model.schema.Login
import com.sakshi.coolshopapplication.model.schema.LoginDetails
import retrofit2.Call
import retrofit2.http.GET

interface LoginApi {
    @GET(Urls.URL_LOGIN)
    fun postLoginAsync(loginDetails: LoginDetails): Call<Login>
}