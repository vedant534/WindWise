package com.example.windwise.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.windwise.model.Favorite
import com.example.windwise.screens.AboutScreen
import com.example.windwise.screens.FavouriteScreen
import com.example.windwise.screens.MainScreen
import com.example.windwise.screens.MainViewModel
import com.example.windwise.screens.SearchScreen
import com.example.windwise.screens.SettingScreen
import com.example.windwise.screens.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ){
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable( "$route/{city}",
            arguments= listOf(
                navArgument("city"){
                    type = NavType.StringType
                }
            )
        ) {navBack->

            navBack.arguments?.getString("city").let {city->

                val mainViewModel = hiltViewModel<MainViewModel>()

                MainScreen(navController = navController,
                    mainViewModel,
                    city=city)

            }

        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.FavouriteScreen.name) {
            FavouriteScreen(navController = navController)
        }

        composable(WeatherScreens.SettingScreen.name) {
            SettingScreen(navController = navController)
        }


    }
}