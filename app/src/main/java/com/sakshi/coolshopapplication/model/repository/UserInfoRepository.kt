package com.sakshi.coolshopapplication.model.repository

import com.sakshi.coolshopapplication.model.apis.UserInfoApi
import com.sakshi.coolshopapplication.model.schema.UserAvatar
import com.sakshi.coolshopapplication.model.schema.UserInfo
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import java.io.File

class UserInfoRepository(private val userInfoApi: UserInfoApi) {

    private lateinit var userInfoCall: Call<UserInfo>
    private lateinit var userAvatarCall: Call<UserAvatar>
    private lateinit var userInfoListener: UserInfoListener

    fun setListeners(userInfoListener: UserInfoListener) {
        this.userInfoListener = userInfoListener
    }

    fun getInfo(userId: String) {
        userInfoCall = userInfoApi.getUserInfo(userId)
        userInfoCall.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.code() == 200) {
                    userInfoListener.onUserInfoFetched(response.body()!!)
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                userInfoListener.onFailure()
            }
        })
    }

    fun changeAvatar(userId: String, imagePath: File) {
        userAvatarCall = userInfoApi.uploadFile(userId, imagePath)!!
        userAvatarCall.enqueue(object : Callback<UserAvatar> {
            override fun onResponse(call: Call<UserAvatar>, response: Response<UserAvatar>) {
                if (response.code() == 200) {
                    userInfoListener.onAvatarChanged(response.body()!!.avatarUrl)
                }
            }

            override fun onFailure(call: Call<UserAvatar>, t: Throwable) {
                userInfoListener.onFailure()
            }
        })
    }

    fun cancelApiCalls() {
        userInfoCall.cancel()
        userAvatarCall.cancel()
    }

    interface UserInfoListener {
        fun onUserInfoFetched(userInfo: UserInfo)
        fun onAvatarChanged(url : String)
        fun onFailure()
    }

}