package com.example.androidprojetparkour.vue

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.ObstacleViewModel

@Composable
fun vueNewObstacle(viewModel: ViewModelProvider, navController: NavHostController, data: Int,) {
    var textName = remember { mutableStateOf("") };
    val context = LocalContext.current

    val viewModelObstacles  = viewModel[ObstacleViewModel::class.java]
    val obstacles = viewModelObstacles.obstacles.observeAsState()


    Column(
        Modifier.fillMaxSize().padding(20.dp),horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(Modifier.padding(10.dp)) {
            Text("New Obstacle")
        }
        Row(Modifier.padding(10.dp)) {
            Text("Name :")
            TextField(
                value = textName.value,
                onValueChange = {
                    textName.value = it
                },
            )
        }

        Row {
            Button(
                onClick = {

                    if(textName.value == ""){
                        Toast.makeText(context,"le nom et vide", Toast.LENGTH_SHORT).show();
                        return@Button
                    }

                    viewModelObstacles.createObstacle(
                        ObstaclesItem(
                            created_at = "",
                            id = -1,
                            name = textName.value,
                            picture = "",
                            updated_at = ""
                        )
                    )

                    navController.navigate(Routes.vueListObstacles + "/" + data);
                }
            ){
                Text("Validée")
            }
        }

        Row {
            Button(
                onClick = {
                    navController.navigate(Routes.vueListObstacles + "/" + data);
                }
            ){
                Text("Cancel")
            }
        }
    }
}