package com.example.session2.model.models

sealed class SignUpEventState {
    data class UpdateName(val newName: String) : SignUpEventState()
    data class UpdatePhone(val newPhone: String) : SignUpEventState()
    data class UpdateEmail(val newEmail: String) : SignUpEventState()
    data class UpdatePass(val newPass: String) : SignUpEventState()
    data class UpdatePassV(val newPassV: String) : SignUpEventState()
    data class UpdateCheck(val newCheck: Boolean) : SignUpEventState()
}

data class SignUpScreenState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val passwordV: String = "",
    val check: Boolean = false,
)