package com.sakshi.coolshopapplication.view.display

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sakshi.coolshopapplication.model.repository.UserInfoRepository
import com.sakshi.coolshopapplication.model.schema.UserInfo
import java.io.File

class DisplayViewModel : ViewModel(), UserInfoRepository.UserInfoListener {

    private lateinit var userInfoRepository: UserInfoRepository

    var liveDataUserInfo = MutableLiveData<UserInfo>()

    fun setRepository(userInfoRepository: UserInfoRepository, userId: String) {
        this.userInfoRepository = userInfoRepository;
        userInfoRepository.setListeners(this)
        userInfoRepository.getInfo(userId)
    }

    fun changeAvatar(userId: String, file: File) {
        userInfoRepository.changeAvatar(userId, file)
    }

    fun cancelApiCall(userInfoRepository: UserInfoRepository) {
        userInfoRepository.cancelApiCalls()
    }

    override fun onUserInfoFetched(userInfo: UserInfo) {
        liveDataUserInfo.value = userInfo
    }

    override fun onAvatarChanged(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure() {
        //api fails here
    }

}
