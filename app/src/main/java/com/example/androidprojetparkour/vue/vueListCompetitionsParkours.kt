package com.example.androidprojetparkour.vue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController

@Composable
fun vueListCompetitionsParkours(
    viewModel: ViewModelProvider,
    competition: Int,
    navController: NavHostController
){
    Column {
        Spacer(Modifier.height(500.dp))
        Text("id " + competition)
    }
}