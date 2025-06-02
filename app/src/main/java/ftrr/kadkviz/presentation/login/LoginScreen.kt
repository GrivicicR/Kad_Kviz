package ftrr.kadkviz.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ftrr.kadkviz.ui.theme.inverseSurfaceLight
import ftrr.kadkviz.ui.theme.primaryContainerLight

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val uiState by viewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginState.Success -> {
                if (state.user != null) {
                    Toast.makeText(
                        context,
                        "Login Successful: ${state.user.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                onLoginSuccess()
                viewModel.resetUiState()
            }

            is LoginState.Error -> {
                Toast.makeText(context, "Login Error: ${state.message}", Toast.LENGTH_SHORT).show()
                viewModel.resetUiState()
            }

            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryContainerLight),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryContainerLight)
        )
        {
            Spacer(Modifier.size(48.dp))

            Text(
                text = "PRIJAVA",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = inverseSurfaceLight,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.size(48.dp))

            Card(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(inverseSurfaceLight)
                        .padding(16.dp),
                ) {
                    Text(
                        text = "Korisničko ime:"
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.size(24.dp))

                    Text(
                        text = "Lozinka:"
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.size(48.dp))

                    Button(
                        onClick = {
                            if (email.isNotBlank() && password.isNotBlank()) {
                                viewModel.signInWithEmailPassword(email = email, pass = password)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter email and password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Pošalji"
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        viewModel = LoginViewModel(),
        modifier = Modifier,
        onLoginSuccess = {},
        onNavigateToSignUp = {})
}