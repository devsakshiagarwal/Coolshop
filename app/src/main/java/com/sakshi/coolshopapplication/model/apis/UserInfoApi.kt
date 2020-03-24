package com.sakshi.coolshopapplication.model.apis

import com.sakshi.coolshopapplication.arch.Urls
import com.sakshi.coolshopapplication.model.schema.UserAvatar
import com.sakshi.coolshopapplication.model.schema.UserInfo
import retrofit2.Call
import retrofit2.http.*
import java.io.File


interface UserInfoApi {

    @GET(Urls.URL_USER_INFO)
    fun getUserInfo(@Path("user_id") id: String): Call<UserInfo>

    @POST(Urls.URL_AVATAR)
    @Multipart
    fun uploadFile(@Path("user_id") id: String, @Part("avatar") imageFile: File): Call<UserAvatar>?
}