package com.sakshi.coolshopapplication.arch

import androidx.fragment.app.FragmentActivity
import com.sakshi.coolshopapplication.model.repository.LoginRepository
import com.sakshi.coolshopapplication.model.repository.UserInfoRepository

class CompRootUi(
    private val compRoot: CompRoot,
    private val activityContext: FragmentActivity
) {

    fun getRouter() = Router(activityContext.supportFragmentManager)

    fun getLoginRepo() = LoginRepository(compRoot.getLoginApi())

    fun getUserInfoRepo() = UserInfoRepository(compRoot.getUserInfoApi())

}