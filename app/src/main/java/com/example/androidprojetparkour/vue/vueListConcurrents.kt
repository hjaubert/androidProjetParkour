package com.example.androidprojetparkour.vue

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController

@Composable
fun vueListConcurents(viewModel: ViewModelProvider, data: Int, navController: NavHostController) {
    Text("" + data)
}