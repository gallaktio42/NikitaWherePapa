package com.example.session2.model.models

sealed class NewPasswordEventState {
    data class UpdatePass (val newPass: String): NewPasswordEventState()
    data class UpdatePassV (val newPassV: String): NewPasswordEventState()
}

data class NewPasswordScreenState(
    val password: String = "",
    val passwordV: String = "",
)