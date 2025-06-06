package com.example.androidprojetparkour.router

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidprojetparkour.vue.vueArbitrage
import com.example.androidprojetparkour.vue.vueListCompetition
import com.example.androidprojetparkour.vue.vueListCompetitionsCompetitors
import com.example.androidprojetparkour.vue.vueListCompetitionsCompetitorsAdd
import com.example.androidprojetparkour.vue.vueListCompetitionsParkours
import com.example.androidprojetparkour.vue.vueListConcurents
import com.example.androidprojetparkour.vue.vueListObstaclesDisponible
import com.example.androidprojetparkour.vue.vueListObstales
import com.example.androidprojetparkour.vue.vueNewCompetition
import com.example.androidprojetparkour.vue.vueNewCompetitors
import com.example.androidprojetparkour.vue.vueNewParkour
import com.example.androidprojetparkour.vue.vueNewObstacle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Router(viewModel: ViewModelProvider) {

    val navController = rememberNavController()

    NavHost(navController =  navController, startDestination = Routes.vueListCompetitions, builder = {
        composable(Routes.vueNewCompetition){
            vueNewCompetition(viewModel,navController)
        }
        composable(Routes.vueNewObstacle +"/{data}/{idCompetitor}"){
            val dataStringIdCompetitor = it.arguments?.getString("idCompetitor")
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueNewObstacle(viewModel,navController,data,dataStringIdCompetitor)
        }

        composable(Routes.vueListCompetitions){
            vueListCompetition(viewModel,navController)
        }
        composable(Routes.vueListCompetitionsParkours +"/{data}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueListCompetitionsParkours(viewModel,data,navController)
        }
        composable(Routes.vueListCompetitionsCompetitors +"/{data}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueListCompetitionsCompetitors(viewModel,data,navController)
        }
        composable(Routes.vueListCompetitionsCompetitorsAdd +"/{data}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueListCompetitionsCompetitorsAdd(viewModel,data,navController)
        }
        composable(Routes.vueListObstacles +"/{coursId}/{idCompetitor}"){
            val dataStringIdCompetitor = it.arguments?.getString("idCompetitor")
            val coursIdString = it.arguments?.getString("coursId")
            val coursId = coursIdString?.toInt() ?:-1
            vueListObstales(viewModel,coursId,navController,dataStringIdCompetitor)
        }
        composable(Routes.vueListObstaclesDisponible +"/{data}/{idCompetitor}"){
            val dataStringIdCompetitor = it.arguments?.getString("idCompetitor")
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueListObstaclesDisponible(viewModel,data,navController,dataStringIdCompetitor)
        }

        composable(Routes.vueNewParkour +"/{data}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueNewParkour(viewModel,navController,data)
        }

        composable(Routes.vueArbitrage +"/{idCourse}/{idCompetitor}/{idCompetition}"){
            val dataStringIdCourse = it.arguments?.getString("idCourse")
            val dataStringIdCompetitor = it.arguments?.getString("idCompetitor")
            val dataStringIdCompetition = it.arguments?.getString("idCompetition")
            val idCourse = dataStringIdCourse?.toInt() ?:-1
            val idCompetition = dataStringIdCompetition?.toInt() ?:-1
            vueArbitrage(viewModel, idCourse, idCompetition)
        }
        composable(Routes.vueListConcurents +"/{data}/{idCompetition}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            val dataStringCompet = it.arguments?.getString("idCompetition")
            val dataId = dataStringCompet?.toInt() ?:-1
            vueListConcurents(viewModel,data,navController,dataId)
        }
        composable(Routes.vueNewCompetitors +"/{data}"){
            val dataString = it.arguments?.getString("data")
            val data = dataString?.toInt() ?:-1
            vueNewCompetitors(viewModel,navController,data)
        }
    })
}