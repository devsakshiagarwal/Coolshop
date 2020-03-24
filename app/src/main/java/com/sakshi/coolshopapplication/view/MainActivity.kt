package com.sakshi.coolshopapplication.view

import android.os.Bundle
import com.sakshi.coolshopapplication.R
import com.sakshi.coolshopapplication.arch.BaseActivity
import com.sakshi.coolshopapplication.arch.SharedPref

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moveToNextPage()
    }

    private fun moveToNextPage() {
        if (SharedPref.getAuthToken(this) == null || SharedPref.getAuthToken(this) == "") {
            compRoot()!!.getRouter().toLoginPage()
        } else {
            compRoot()!!.getRouter().toDisplayPage()
        }

    }
}
