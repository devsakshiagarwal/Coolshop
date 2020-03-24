package com.sakshi.coolshopapplication

import android.app.Application
import com.sakshi.coolshopapplication.arch.CompRoot

class CoolShop : Application() {

    private lateinit var compRoot: CompRoot

    override fun onCreate() {
        super.onCreate()
        compRoot = CompRoot(this)
    }

    fun getCompRoot() = compRoot
}