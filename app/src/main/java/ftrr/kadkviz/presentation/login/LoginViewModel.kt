package ftrr.kadkviz.presentation.login

import android.util.Log
import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser

            if (firebaseAuth.currentUser == null && _loginState.value is LoginState.Success) {
                _loginState.value = LoginState.Success(null)
            } else if (firebaseAuth.currentUser != null && _loginState.value is LoginState.Loading) {
                _loginState.value = LoginState.Success(firebaseAuth.currentUser)
            }
        }

    }

    fun signUpWithEmailPassword(email: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val result = auth.createUserWithEmailAndPassword(email, pass).await()
                _loginState.value = LoginState.Success(result.user)
                _currentUser.value = result.user
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Sign up failed")
                Log.e("AuthViewModel", "Sign Up Error: ${e.message}", e)
            }
        }
    }

    fun signInWithEmailPassword(email: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, pass).await()
                _loginState.value = LoginState.Success(result.user)
                _currentUser.value = result.user
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Sign in failed")
                Log.e("AuthViewModel", "Sign In Error: ${e.message}", e)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            // _uiState.value = AuthUiState.Loading // Optional: show loading during sign out
            try {
                auth.signOut()
                _loginState.value = LoginState.Initial
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Sign out failed")
            }
        }
    }

    fun resetUiState() {
        _loginState.value = LoginState.Initial
    }
}