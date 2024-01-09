package projekt.koncowy.kalkulator

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// nawigacja

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Start") {

        composable(route= "MainKalkulator"){
            val viewModel = viewModel<KalkulatorViewModel>()
            Mainkalkulator(navController, onAction = viewModel::onAction, viewModel)
        }
        composable(route= "Start"){

            Start(navController)
        }

    }
}