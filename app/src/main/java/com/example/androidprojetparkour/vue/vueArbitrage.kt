package com.example.androidprojetparkour.vue

import android.annotation.SuppressLint
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
import com.example.androidprojetparkour.viewModel.ObstacleViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun vueArbitrage(viewModel: ViewModelProvider, data: Int) {

    val obstacleViewModel = viewModel[ObstacleViewModel::class.java]
    var (numObstacle, setNumObstacle) = remember { mutableIntStateOf(1) }
    var (nbObstacles, setnbObstacles) = remember { mutableIntStateOf(0) }
    var (tempsObstacle, setTempsObstacle) = remember { mutableLongStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(25.dp)) {
        ListObstacles(obstacleViewModel, numObstacle, data) { nb -> setnbObstacles(nb) }
        Chronometre(numObstacle, nbObstacles, { num -> setNumObstacle(num) }, { temps -> setTempsObstacle(temps)}, tempsObstacle)
    }
}

@Composable
fun ListObstacles(
    obstacleViewModel: ObstacleViewModel,
    numObstacle: Int,
    data: Int,
    setNbObstacles: (Int) -> Unit
){
    val obstacles = obstacleViewModel.courseObstacles.observeAsState()
    LaunchedEffect(Unit) {
        obstacleViewModel.getCourseObstacles(data)
    }
    Column {
        when(val result = obstacles.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                setNbObstacles(result.data.size)
                afficheObstacleEnCours(result.data, numObstacle)
            }
            null -> {}
        }

    }
}

@Composable
fun afficheObstacleEnCours(data: ObstaclesCourse, numObstacle: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = data[numObstacle].obstacle_name)
    }
}

@Composable
fun Chronometre(
    numObstacle: Int,
    nbObstacles: Int,
    setNumObstacle: (Int) -> Unit,
    setTempsObstacle: (Long) -> Unit,
    tempsObstacle: Long
) {

    var time by remember { mutableLongStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var startTime by remember { mutableLongStateOf(0) }
    //val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxSize().padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = formatTime(timeMi = time, setTempsObstacle),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(9.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row{
            Button(
                onClick = {
                    if(isRunning){
                        isRunning = false
                    } else {
                        startTime = System.currentTimeMillis() - time
                        isRunning = true
                        //keyboardController?.hide()
                    }
                }, modifier = Modifier.weight(1f)
            ) {
                Text(text = if(isRunning) "Pause" else "Lancer", color = Color.White)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(onClick = {
                if(numObstacle == nbObstacles - 1){
                    isRunning = false
                    //Afficher vue de confirmation
                    //Si il y a plus de concurrents, passer au classement
                }
                time = 0
                if(isRunning){
                    //Log.d("Temps", tempsObstacle.toString())
                    //Ajout temps dans la bdd
                    setNumObstacle(numObstacle + 1)
                }
                isRunning = false
            }, modifier = Modifier.weight(1f)) {

                Text(text = "Prochain Obstacle", color = Color.White)

            }

        }
    }

    LaunchedEffect(isRunning) {

        while(isRunning){
            delay(1000)
            time = System.currentTimeMillis() - startTime
        }

    }

}

@SuppressLint("DefaultLocale")
@Composable
fun formatTime(timeMi: Long, setTempsObstacle: (Long) -> Unit): String{

    val millisecondes = TimeUnit.MILLISECONDS.toMillis(timeMi)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMi) % 60
    val secondes = TimeUnit.MILLISECONDS.toSeconds((timeMi)) % 60
    setTempsObstacle(millisecondes)

    return String.format("%02d:%02d", minutes, secondes)
}
