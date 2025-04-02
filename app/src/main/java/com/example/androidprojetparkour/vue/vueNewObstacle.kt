package com.example.androidprojetparkour.vue

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.models.obstacles.ObstaclePost
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CourseViewModel
import com.example.androidprojetparkour.viewModel.ObstacleViewModel

@Composable
fun vueNewObstacle(
    viewModel: ViewModelProvider,
    navController: NavHostController,
    data: Int,
    dataStringIdCompetitor: String?,) {
    var textName = remember { mutableStateOf("") };
    val context = LocalContext.current

    val viewModelObstacles  = viewModel[ObstacleViewModel::class.java]
    val viewModelCourses  = viewModel[CourseViewModel::class.java]

    LaunchedEffect(Unit) {
        viewModelObstacles.getObstacles()
    }



    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(Modifier.padding(10.dp)) {
            Text("New Obstacle",fontSize = 40.sp)
        }
        Row(Modifier.padding(10.dp)) {
            TextField(
                value = textName.value,
                onValueChange = {
                    textName.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = {Text("Name :")},
                leadingIcon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Person,
                        contentDescription = null,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFA500),
                    unfocusedContainerColor = Color(0xFFFFA500),
                )
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
        Button(
                onClick = {

                    if(textName.value == ""){
                        Toast.makeText(context,"le nom et vide", Toast.LENGTH_SHORT).show();
                        return@Button
                    }

                    val newObstacle = ObstaclesItem(
                        created_at = "",
                        id = -1,
                        name = textName.value,
                        picture = "",
                        updated_at = ""
                    )

                    viewModelObstacles.createObstacle(newObstacle)

                    val obsaclepost = ObstaclePost(newObstacle.id)

                    viewModelCourses.addObstacleToCourse(data,obsaclepost)


                    navController.navigate(Routes.vueListObstaclesDisponible + "/" + data + "/" + dataStringIdCompetitor);
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp, bottom = 50.dp)
            ){
                Text("Validée")
            }


            Button(
                onClick = {
                    navController.navigate(Routes.vueListObstaclesDisponible + "/" + data + "/" + dataStringIdCompetitor);
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp, bottom = 50.dp)
            ){
                Text("Cancel")
            }
        }
    }
}