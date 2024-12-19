package com.example.session2.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.session2.model.models.OTPVerifyEventState
import com.example.session2.model.models.OTPVerifyScreenState
import com.example.session2.model.data.Screens
import com.example.session2.model.network.Supabase.supabase
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OTPVerifyViewmodel : ViewModel() {
    var state by mutableStateOf(OTPVerifyScreenState())
        private set

    private val _time = MutableStateFlow(60)
    val time: StateFlow<Int> = _time

    private val _show = MutableStateFlow(false)
    val show: StateFlow<Boolean> = _show

    init {
        startCountdown()
    }

    fun onChange(event: OTPVerifyEventState) {
        when (event) {
            is OTPVerifyEventState.UpdateToken -> state = state.copy(token = event.newToken)
        }
    }

    fun enabled(): Boolean {
        return state.token.isNotEmpty() && state.token.length == 6
    }

    private fun startCountdown() {
        viewModelScope.launch {
            while (_time.value > 0) {
                delay(1000L)
                _time.value--
            }
            _show.value = true
        }
    }

    fun resetCountdown(){
        _time.value = 60
        _show.value = false
        startCountdown()
    }

    fun verifyOTP(context: Context, email: String, token: String, navController: NavController) {
        viewModelScope.launch {
            try {
                supabase.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, token)
                Toast.makeText(context, "Verify Success", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ScreenNewPass.route)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun otpMessage(context: Context, email: String) {
        viewModelScope.launch {
            try {
                supabase.auth.resetPasswordForEmail(email)
                Toast.makeText(context, "Send!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}