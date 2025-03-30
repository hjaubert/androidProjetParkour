package com.example.androidprojetparkour.vue

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.viewModel.CompetitionViewModel

@Composable
fun vueListCompetitionsParkours(viewModel: ViewModelProvider, competition: Int){
    Text("Parkours")
}