package com.sakshi.coolshopapplication.model.schema

import com.squareup.moshi.Json

data class UserAvatar(
    @field:Json(name = "avatar_url") val avatarUrl: String = ""
)
