package ftrr.kadkviz.presentation.login

import com.google.firebase.auth.FirebaseUser

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    data class Error(val message: String) : LoginState()
    data class Success(val user: FirebaseUser?): LoginState()
}