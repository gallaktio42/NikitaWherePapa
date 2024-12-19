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
import com.example.session2.model.models.NewPasswordEventState
import com.example.session2.model.models.NewPasswordScreenState
import com.example.session2.model.data.Screens
import com.example.session2.model.network.Supabase.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class NewPasswordViewmodel : ViewModel() {
    var state by mutableStateOf(NewPasswordScreenState())
        private set

    var visibility by mutableStateOf(false)
    var visibilityV by mutableStateOf(false)

    fun onChange (event: NewPasswordEventState){
        state = when (event){
            is NewPasswordEventState.UpdatePass -> state.copy(password = event.newPass)
            is NewPasswordEventState.UpdatePassV -> state.copy(passwordV= event.newPassV)
        }
    }

    fun enabledForNewPass(): Boolean {
        return state.password.isNotEmpty() && state.passwordV.isNotEmpty()
                && state.passwordV == state.password && state.password.length >=6 && state.passwordV.length >=6
    }

    fun newPassword(context: Context, navController: NavController){
        viewModelScope.launch {
            try {
                supabase.auth.updateUser {
                    password = state.passwordV
                }
                Toast.makeText(context, "New Password is success", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ScreenLoginRoute.route){
                    popUpTo(navController.graph.findStartDestination().id)
                }
            }catch (e:Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}