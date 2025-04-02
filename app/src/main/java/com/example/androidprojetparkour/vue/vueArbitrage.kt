package com.example.androidprojetparkour.vue

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.androidprojetparkour.api.models.obstacles.ObstacleCourseItem
import com.example.androidprojetparkour.bdd.PerformanceObstacleBddViewModel
import com.example.androidprojetparkour.bdd.view_models.PerformancesBddViewModel
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesCourse
import com.example.androidprojetparkour.viewModel.CompetitorViewModel
import com.example.androidprojetparkour.viewModel.ObstacleViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun vueArbitrage(viewModel: ViewModelProvider, idCourse: Int, idCompetitor: Int) {

    val obstacleViewModel = viewModel[ObstacleViewModel::class.java]
    val competitorViewModel = viewModel[CompetitorViewModel::class.java]
    var (numObstacle, setNumObstacle) = remember { mutableIntStateOf(1) }
    var (nbObstacles, setnbObstacles) = remember { mutableIntStateOf(0) }
    var (tempsObstacle, setTempsObstacle) = remember { mutableLongStateOf(0) }
    var (nbObstacleTraverse, setnbObstacleTraverse) = remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(25.dp)) {
        ListObstacles(obstacleViewModel, competitorViewModel ,numObstacle, idCourse) { nb -> setnbObstacles(nb) }
        Chronometre(numObstacle, nbObstacles, { num -> setNumObstacle(num) }, tempsObstacle, { temps -> setTempsObstacle(temps) }, obstacleViewModel, idCourse, idCompetitor, viewModel)
    }
}

@Composable
fun ListObstacles(
    obstacleViewModel: ObstacleViewModel,
    competitorViewModel: CompetitorViewModel,
    numObstacle: Int,
    idCourse: Int,
    setNbObstacles: (Int) -> Unit
){
    val obstacles = obstacleViewModel.courseObstacles.observeAsState()
    LaunchedEffect(Unit) {
        obstacleViewModel.getCourseObstacles(idCourse)
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
    tempsObstacle: Long,
    setTempsObstacle: (Long) -> Unit,
    obstacleViewModel: ObstacleViewModel,
    idCourse: Int,
    idCompetitor: Int,
    viewModel: ViewModelProvider
) {

    var time by remember { mutableLongStateOf(0) }
    var tempsByObstacle by remember { mutableLongStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var startTime by remember { mutableLongStateOf(0) }
    var startTimeObstacle by remember { mutableLongStateOf(0) }

    val perfObstacleBddViewModel = viewModel[PerformanceObstacleBddViewModel::class.java]
    val perfBddViewModel = viewModel[PerformancesBddViewModel::class.java]

    perfBddViewModel.ajouterPerformance(idCompetitor, idCourse, "En cours", time)
    val performance = perfBddViewModel.getPerformance(idCourse, idCompetitor)

    val obstacles = obstacleViewModel.courseObstacles
    val result = obstacles.value
    LaunchedEffect(Unit) {
        obstacleViewModel.getCourseObstacles(idCourse)
    }
    var listObstacle = listOf<ObstacleCourseItem>()
    if(result is NetworkResponse.Success){
        listObstacle = result.data
    }


    Column(modifier = Modifier.fillMaxSize().padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = formatTime(time, tempsByObstacle, setTempsObstacle),
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
                        startTimeObstacle = System.currentTimeMillis() - tempsObstacle
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
                tempsByObstacle = 0
                startTimeObstacle = System.currentTimeMillis() - tempsByObstacle
                startTime = System.currentTimeMillis() - time
                if(isRunning){
                    Log.d("Temps", time.toString())
                    Log.d("Temps", tempsObstacle.toString())

                    perfObstacleBddViewModel.ajouterPerformanceObstacle(listObstacle[numObstacle].id, performance.id, tempsByObstacle, 0, 0)

                    setNumObstacle(numObstacle + 1)
                }
                //isRunning = false
            }, modifier = Modifier.weight(1f)) {

                Text(text = "Prochain Obstacle", color = Color.White)

            }

        }
    }

    LaunchedEffect(isRunning) {

        while(isRunning){
            delay(1000)
            time = System.currentTimeMillis() - startTime
            tempsByObstacle = System.currentTimeMillis() - startTimeObstacle

        }

    }

}

@SuppressLint("DefaultLocale")
@Composable
fun formatTime(timeMiTotal: Long, timeMiObstacle: Long, setTempsObstacle: (Long) -> Unit): String{

    val millisecondes = TimeUnit.MILLISECONDS.toMillis(timeMiObstacle)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMiTotal) % 60
    val secondes = TimeUnit.MILLISECONDS.toSeconds((timeMiTotal)) % 60
    setTempsObstacle(millisecondes)

    return String.format("%02d:%02d", minutes, secondes)
}
