package com.sakshi.coolshopapplication.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sakshi.coolshopapplication.arch.Router
import com.sakshi.coolshopapplication.model.repository.LoginRepository
import com.sakshi.coolshopapplication.model.schema.LoginDetails
import com.sakshi.coolshopapplication.utils.CoreUtils

class LoginViewModel : ViewModel() {

    var liveDataErrorEmail = MutableLiveData<Boolean>(false)
    var liveDataErrorPassword = MutableLiveData<Boolean>(false)
    var liveDataLoginSuccess = MutableLiveData<Boolean>(false)

    private lateinit var router: Router
    private lateinit var loginRepository: LoginRepository

    fun setRepository(router: Router, loginRepository: LoginRepository) {
        this.router = router
        this.loginRepository = loginRepository
    }

    fun cancelApiCall() {
        loginRepository.cancelApiCalls()
    }

    fun doLogin(loginDetails: LoginDetails) {
        if(!CoreUtils.isEmailValid(loginDetails.email)) {
            liveDataErrorEmail.value = true
            return
        }
        if(loginDetails.password.length <= 8) {
            liveDataErrorPassword.value = true
            return
        }
//        login through api
//        loginRepository.doLogin(loginDetails)
        liveDataLoginSuccess.value = true
    }
}
