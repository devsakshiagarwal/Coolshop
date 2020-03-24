package com.sakshi.coolshopapplication.model.repository

import com.sakshi.coolshopapplication.model.apis.LoginApi
import com.sakshi.coolshopapplication.model.schema.Login
import com.sakshi.coolshopapplication.model.schema.LoginDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val loginApi: LoginApi) {

    private lateinit var loginCall : Call<Login>
    private lateinit var loginListener: LoginListener

    fun setListeners(loginListener: LoginListener) {
        this.loginListener = loginListener
    }

    fun doLogin(loginDetails: LoginDetails) {
        loginCall = loginApi.postLoginAsync(loginDetails)
        loginCall.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.code() == 200) {
                    loginListener.onLoginSuccess(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                loginListener.onFailure(t.localizedMessage)
            }
        })
    }

    fun cancelApiCalls() {
        loginCall.cancel()
    }

    interface LoginListener {
        fun onLoginSuccess(login: Login)
        fun onFailure(error: String)
    }

}