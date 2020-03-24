package com.sakshi.coolshopapplication.model.schema

import com.squareup.moshi.Json

data class LoginDetails(
    @field:Json(name = "email") val email: String = "",
    @field:Json(name = "password") val password: String = ""
)
