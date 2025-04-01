package com.example.androidprojetparkour.router

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidprojetparkour.vue.vueInfoCompetition
import com.example.androidprojetparkour.vue.vueListCompetition
import com.example.androidprojetparkour.vue.vueListCompetitionsCompetitors
import com.example.androidprojetparkour.vue.vueListCompetitionsParkours

@Composable
fun Router(viewModel: ViewModelProvider) {

    val navController = rememberNavController()

    NavHost(navController =  navController, startDestination = Routes.vueListCompetitions, builder = {
        composable(Routes.vueListCompetitions){
            vueListCompetition(viewModel,navController)
        }
        composable(Routes.vueListCompetitionsParkours +"/{data}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueListCompetitionsParkours(viewModel,data)
        }
        composable(Routes.vueListCompetitionsCompetitors +"/{data}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueListCompetitionsCompetitors(viewModel,data)
        }

    })
}