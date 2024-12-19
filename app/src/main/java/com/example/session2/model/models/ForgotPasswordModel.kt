package com.example.session2.model.models

sealed class ForgotPasswordEventState {
    data class UpdateEmail (val newEmail: String): ForgotPasswordEventState()
}

data class ForgotPasswordScreenState(
    val email: String = "",
)