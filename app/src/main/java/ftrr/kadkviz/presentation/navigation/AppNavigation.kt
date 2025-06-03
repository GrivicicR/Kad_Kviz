package ftrr.kadkviz.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ftrr.kadkviz.presentation.KadKvizViewModel
import ftrr.kadkviz.presentation.home.HomeScreen
import ftrr.kadkviz.presentation.login.LoginScreen
import ftrr.kadkviz.presentation.login.LoginViewModel
import ftrr.kadkviz.presentation.organiziraj.OrganizirajScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: KadKvizViewModel,
    loginViewModel: LoginViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = LoginScreen
    ) {
        composable<HomeScreen> {
            HomeScreen(
                viewModel = viewModel
            )
        }

        composable<LoginScreen> {
            LoginScreen(
                modifier = modifier,
                onLoginSuccess = {
                    navController.navigate(route = HomeScreen)
                },
                viewModel = loginViewModel
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