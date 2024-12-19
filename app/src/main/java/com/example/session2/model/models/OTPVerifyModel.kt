package com.example.session2.model.models

sealed class OTPVerifyEventState{
    data class UpdateToken(val newToken: String): OTPVerifyEventState()
}

data class OTPVerifyScreenState(
    val token: String = "",
)