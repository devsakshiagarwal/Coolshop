package com.sakshi.coolshopapplication.model.schema

import com.squareup.moshi.Json

data class Login(
    @field:Json(name = "userid") val userId: String = "",
    @field:Json(name = "token") val token: String = ""
)
