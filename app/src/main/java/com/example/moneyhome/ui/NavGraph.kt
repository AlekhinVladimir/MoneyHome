package com.example.moneyhome.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable// переделать. компост использовать не будем в проекте
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            HomeScreen()
        }
        composable(route = "menu") {
            MenuScreen()
        }
        composable(route = "add") {
            AddScreen()
        }
        composable(route = "history") {
            HistoryScreen()
        }
        composable(route = "analytics") {
            AnalyticsScreen()
        }
    }
}