package com.sakshi.coolshopapplication.model.schema

import com.squareup.moshi.Json

data class UserInfo(
    @field:Json(name = "email") val email: String = "",
    @field:Json(name = "avatar_url") val avatarUrl: String = ""
)
