package ftrr.kadkviz.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ftrr.kadkviz.presentation.HomeScreen
import ftrr.kadkviz.presentation.KadKvizViewModel
import ftrr.kadkviz.presentation.LoginScreen
import ftrr.kadkviz.presentation.OrganizirajScreen
import ftrr.kadkviz.presentation.PretragaScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: KadKvizViewModel
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
            LoginScreen(modifier = modifier,
                onLoginClick = {
                    navController.navigate(route = HomeScreen)
                })
        }

        composable<OrganizirajScreen> {
            OrganizirajScreen(
                onSendClick = {
                    viewModel.insertKviz(it)
                    navController.navigate(route = HomeScreen)
                },
                viewModel = viewModel
            )
        }

        composable<PretragaScreen> {
            PretragaScreen(modifier)
        }
    }
}