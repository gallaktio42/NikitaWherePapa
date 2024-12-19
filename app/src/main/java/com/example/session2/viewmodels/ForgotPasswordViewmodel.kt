package com.example.session2.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.session2.model.models.ForgotPasswordEventState
import com.example.session2.model.models.ForgotPasswordScreenState
import com.example.session2.model.data.Screens
import com.example.session2.model.data.SignUpUserTable
import com.example.session2.model.network.Supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class ForgotPasswordViewmodel : ViewModel() {
    var state by mutableStateOf(ForgotPasswordScreenState())
        private set

    init {
        viewModelScope.launch {
            emailUserResponse()
        }
    }

    fun onChange(event: ForgotPasswordEventState) {
        when (event) {
            is ForgotPasswordEventState.UpdateEmail -> state = state.copy(email = event.newEmail)
        }
    }

    fun enabledForForgotPass(): Boolean {
        return state.email.isNotEmpty() && isEmailValid()
    }

    fun isEmailValid(): Boolean {
        return state.email.all {
            it.isLowerCase() || it.isDigit() || it in listOf(
                '@', '-', '_', '.'
            )
        } && state.email.contains('@') && state.email.contains('.') && state.email.length > 5
    }

    fun otpMessage(context: Context, email: String, navController: NavController) {
        viewModelScope.launch {
            try {
                if (emailUserResponse()) {
                    supabase.auth.resetPasswordForEmail(email)
                    Toast.makeText(context, "Send!", Toast.LENGTH_SHORT).show()
                    navController.navigate("${Screens.ScreenSetPass.route}/${state.email}"){
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                } else {
                    Toast.makeText(context, "This user not exist", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun emailUserResponse(): Boolean {
        try {
            val emailUserResponse = supabase.from("SignUpUsers")
                .select()
                .decodeList<SignUpUserTable>()
            val user = emailUserResponse.find { it.email == state.email }
            if (user != null) {
                return true
            } else {
                return false
            }
        }catch (e:Exception){
            throw e
        }
    }
}