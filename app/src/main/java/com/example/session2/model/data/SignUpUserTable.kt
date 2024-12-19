package com.example.session2.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpUserTable (
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("email")
    val email: String,

    @SerialName("created_at")
    val createdAt: String? = null,
)