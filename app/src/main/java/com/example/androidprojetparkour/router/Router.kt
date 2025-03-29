package com.example.androidprojetparkour.router

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidprojetparkour.vue.vueInfoCompetition
import com.example.androidprojetparkour.vue.vueListCompetition

@Composable
fun Router(viewModel: ViewModelProvider) {

    val navController = rememberNavController()

    NavHost(navController =  navController, startDestination = Routes.vueListCompetitions, builder = {
        composable(Routes.vueListCompetitions){
            vueListCompetition(viewModel,navController)
        }
        composable(Routes.vueInfoCompetition+"/{data}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueInfoCompetition(viewModel,data)
        }

    })
}