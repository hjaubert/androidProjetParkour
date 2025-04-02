package com.example.androidprojetparkour

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesCourse
import com.example.androidprojetparkour.router.Router
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
import com.example.androidprojetparkour.viewModel.ObstacleViewModel
import com.example.androidprojetparkour.vue.vueArbitrage
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

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
