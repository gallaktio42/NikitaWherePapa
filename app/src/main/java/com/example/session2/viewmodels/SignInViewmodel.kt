package com.example.session2.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.session2.model.data.Screens
import com.example.session2.model.models.SignInEventState
import com.example.session2.model.models.SignInScreenState
import com.example.session2.model.network.Supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class SignInViewmodel : ViewModel() {
    var state by mutableStateOf(SignInScreenState())
        private set

    var visibility by mutableStateOf(false)

    fun onChange(event: SignInEventState) {
        when (event) {
            is SignInEventState.UpdateEmail -> state = state.copy(email = event.newEmail)
            is SignInEventState.UpdatePassV -> state = state.copy(passwordV = event.newPassV)
            is SignInEventState.UpdateCheck -> state = state.copy(check = event.newCheck)
        }
    }

    fun isValidEmail(): Boolean {
        return state.email.all {
            it.isLowerCase() || it.isDigit() || it in listOf(
                '@',
                '.',
                '_',
                '-'
            )
        } && (state.email.contains('@') && state.email.contains('.') && state.email.length > 5)
    }

    fun enabled(): Boolean {
        return state.email.isNotEmpty() && state.passwordV.isNotEmpty()
    }

    fun signIn(
        context: Context, emailUser: String,
        passwordUser: String, navController: NavController
    ) {
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email) {
                    email = emailUser
                    password = passwordUser
                }
                Toast.makeText(context, "Sign in success", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ScreenHomeRoute.route){
                    popUpTo(navController.graph.findStartDestination().id)
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signInWithGoogle(
        context: Context,
        navController: NavController
    ){
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Google)
                Toast.makeText(context, "SignIn", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ScreenHomeRoute.route){
                    popUpTo(navController.graph.findStartDestination().id)
                }
            }catch (e: Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
}