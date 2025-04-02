package com.example.androidprojetparkour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.vue.vueArbitrage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = ViewModelProvider(this)
            //Router(viewModel)
            vueArbitrage(viewModel, 2795, 4678)
            /*val id = 0
            val viewModelCompetitions  = ViewModelProvider(this)[CompetitionViewModel::class.java]
            val competition = viewModelCompetitions.oneCompetition.observeAsState()
            LaunchedEffect(Unit) {
                viewModelCompetitions.getOneCompetition(id)
            }*/
        }
    }
}
