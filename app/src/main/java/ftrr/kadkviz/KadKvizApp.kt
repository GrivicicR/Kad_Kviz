package ftrr.kadkviz

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ftrr.kadkviz.presentation.KadKvizTopBar
import ftrr.kadkviz.presentation.KadKvizViewModel
import ftrr.kadkviz.presentation.login.LoginViewModel
import ftrr.kadkviz.presentation.navigation.AppNavigation
import ftrr.kadkviz.presentation.navigation.HomeScreen
import ftrr.kadkviz.presentation.navigation.LoginScreen
import ftrr.kadkviz.presentation.navigation.OrganizirajScreen
import ftrr.kadkviz.presentation.navigation.PretragaScreen
import ftrr.kadkviz.ui.theme.KadKvizTheme

@Composable
fun KadKvizApp() {
    KadKvizTheme {
        val viewModel = KadKvizViewModel(context = LocalContext.current)
        val loginViewModel = LoginViewModel()
        val navController = rememberNavController()
        val context = LocalContext.current

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                KadKvizTopBar(
                    onMenuItemClicked = {
                        when (it) {
                            "Home" -> navController.navigate(route = HomeScreen)
                            "Organiziraj" -> navController.navigate(route = OrganizirajScreen)
                            "Pretraga" -> navController.navigate(route = PretragaScreen)
                            "Prijava" -> {
                                loginViewModel.signOut()
                                navController.navigate(route = LoginScreen)
                            }

                            else -> navController.navigate(route = HomeScreen)
                        }
                    },
                    onHomeClick = {
                        navController.navigate(route = HomeScreen)
                    },
                    onAccountClick = {
                        val currentUser = Firebase.auth.currentUser?.email
                        Toast.makeText(context, currentUser, Toast.LENGTH_SHORT).show()
                    },
                    currentDestination = currentDestination
                )
            })
        { innerPadding ->
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel
            )
        }
    }
}