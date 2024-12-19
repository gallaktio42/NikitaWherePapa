package com.example.session2.model.models

sealed class SignInEventState {
    data class UpdateEmail(val newEmail: String) : SignInEventState()
    data class UpdatePassV(val newPassV: String) : SignInEventState()
    data class UpdateCheck(val newCheck: Boolean) : SignInEventState()
}

data class SignInScreenState(
    val email: String = "",
    val passwordV: String = "",
    val check: Boolean = false,
)