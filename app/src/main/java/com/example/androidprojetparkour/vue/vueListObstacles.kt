package com.example.androidprojetparkour.vue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
import androidx.compose.ui.graphics.Color
import com.example.androidprojetparkour.api.models.obstacles.Obstacles
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesItem
import com.example.androidprojetparkour.viewModel.ObstacleViewModel


@Composable
fun vueListObstales(viewModel: ViewModelProvider, navController: NavHostController){

    val viewModelObstacle = viewModel[ObstacleViewModel::class.java]
    val ObstacleResult = viewModelObstacle.obstacles.observeAsState()

    LaunchedEffect(Unit) {
        viewModelObstacle.getObstacles()
    }
    Column {
        when(val result = ObstacleResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                ParkourDetails(data = result.data,navController)
            }
            null -> {}
        }

    }
}

@Composable
fun ParkourDetails(data: Obstacles, navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Spacer(modifier = Modifier.height(35.dp))

            Text("List of Obstacles", fontSize = 40.sp)


            Spacer(modifier = Modifier.height(25.dp))

            LazyColumn {
                items(data.toList()) {obstacle ->
                    Button({
                        //navController.navigate(Routes.vueInfoCompetition+"/"+obstacle.id)
                    }, modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                        Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                            Text(
                                text = obstacle.name,
                                fontSize = 30.sp
                            )

                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }

        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,  // Couleur de fond
                contentColor = Color.White   // Couleur du texte
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("add", fontSize = 25.sp)
        }
    }
}