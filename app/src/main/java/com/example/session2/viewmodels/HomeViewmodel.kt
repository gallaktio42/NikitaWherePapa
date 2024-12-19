package com.example.session2.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.session2.model.data.Screens
import com.example.session2.model.network.Supabase.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class HomeViewmodel: ViewModel() {

    var sessionState by mutableStateOf<String?>(null)  // Создаем состояние для сессии

    // Запускаем корутину для проверки сессии
    init {
        viewModelScope.launch {
            val userInfo = getInfo() // Получаем информацию о пользователе
            sessionState = userInfo // Обновляем состояние
        }
    }


    fun logOut(context: Context, navController: NavController){
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
                Toast.makeText(context, "Logout success!", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ScreenLoginRoute.route){
                    popUpTo(Screens.ScreenHomeRoute.route){
                        inclusive = true
                    }
                }
            }catch (e:Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun getInfo(): String? {
        val session = supabase.auth.currentSessionOrNull()
        if (session == null) {
            Log.d("Mylog", "${session}")
            return null
        } else {
            supabase.auth.retrieveUserForCurrentSession(updateSession = true)
            supabase.auth.refreshCurrentSession()
            Log.d("Mylog", "${session}")
            return session.toString()
        }
    }
}