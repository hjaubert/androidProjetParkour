package com.example.androidprojetparkour.vue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import com.example.androidprojetparkour.api.models.obstacles.ObstaclePost
import com.example.androidprojetparkour.api.models.obstacles.Obstacles
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CourseViewModel


@Composable
fun vueListObstaclesDisponible(
    viewModel: ViewModelProvider,
    coursId: Int,
    navController: NavHostController,
    dataStringIdCompetitor: String?
){
    val viewModelObstacleDisponible = viewModel[CourseViewModel::class.java]
    val obstacleResult = viewModelObstacleDisponible.unusedObstacles.observeAsState()

    LaunchedEffect(Unit) {
        viewModelObstacleDisponible.getUnusedObstacles(coursId)
    }

    Column {
        when(val result = obstacleResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                listObstacle(result.data,navController,coursId,viewModel,dataStringIdCompetitor)
            }
            null -> {}
        }

    }
}

@Composable
fun listObstacle(
    obstacles: Obstacles,
    navController: NavHostController,
    coursId: Int,
    viewModel: ViewModelProvider,
    dataStringIdCompetitor: String?
){

    val viewModelCours = viewModel[CourseViewModel::class.java]


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {

            Spacer(modifier = Modifier.height(35.dp))

            Text("Add Obstacles", fontSize = 40.sp)


            Spacer(modifier = Modifier.height(25.dp))

            LazyColumn {
                items(obstacles.toList()) {obstacle ->
                    Button({

                        val obstaclesPost = ObstaclePost(obstacle.id)
                        viewModelCours.addObstacleToCourse(coursId,obstaclesPost)
                        navController.navigate(Routes.vueListObstaclesDisponible+"/"+coursId +"/" + dataStringIdCompetitor)


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
                navController.navigate(Routes.vueNewObstacle+"/"+coursId+"/"+dataStringIdCompetitor+"")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp,bottom = 50.dp)
        ) {
            Text("New", fontSize = 25.sp)
        }

        Button(
            onClick = { navController.navigate(Routes.vueListObstacles + "/" + coursId + "/" + dataStringIdCompetitor) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 50.dp)
        ) {
            Text("Back", fontSize = 20.sp)
        }
    }
}