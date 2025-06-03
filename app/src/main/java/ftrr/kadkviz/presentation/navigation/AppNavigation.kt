package ftrr.kadkviz.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ftrr.kadkviz.presentation.home.HomeScreen
import ftrr.kadkviz.presentation.KadKvizViewModel
import ftrr.kadkviz.presentation.organiziraj.OrganizirajScreen
import ftrr.kadkviz.presentation.login.LoginScreen
import ftrr.kadkviz.presentation.login.LoginViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: KadKvizViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = OrganizirajScreen
    ) {
        composable<HomeScreen> {
            HomeScreen(
                viewModel = viewModel
            )
        }

        composable<LoginScreen> {
            val loginViewModel = LoginViewModel()
            LoginScreen(
                modifier = modifier,
                onLoginSuccess = {
                    navController.navigate(route = HomeScreen)
                },
                viewModel = loginViewModel,
                onNavigateToSignUp = { }
            )
        }

        composable<OrganizirajScreen> {
            OrganizirajScreen(
                onSendClick = {
                    navController.navigate(route = HomeScreen)
                },
                viewModel = viewModel
            )
        }
    }
}