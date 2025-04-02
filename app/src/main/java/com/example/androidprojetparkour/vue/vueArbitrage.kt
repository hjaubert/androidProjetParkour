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
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesCourse
import com.example.androidprojetparkour.api.models.performances.PerformancesItem
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstaclesItem
import com.example.androidprojetparkour.viewModel.CompetitorViewModel
import com.example.androidprojetparkour.viewModel.ObstacleViewModel
import com.example.androidprojetparkour.viewModel.PerformanceObstacleViewModel
import com.example.androidprojetparkour.viewModel.PerformanceViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun vueArbitrage(
    viewModel: ViewModelProvider,
    idCourse: Int,
    idCompetitor: Int,
    idCompetition: Int
) {

    val obstacleViewModel = viewModel[ObstacleViewModel::class.java]
    val competitorViewModel = viewModel[CompetitorViewModel::class.java]

    var (numObstacle, setNumObstacle) = remember { mutableIntStateOf(1) }
    var (nbObstacles, setnbObstacles) = remember { mutableIntStateOf(0) }
    var (tempsObstacle, setTempsObstacle) = remember { mutableLongStateOf(0) }
    var (nbObstacleTraverse, setnbObstacleTraverse) = remember { mutableIntStateOf(0) }
    var (time, setTime) = remember { mutableIntStateOf(0) }

    var (nextCompetitor, setNextCompetitor) = remember { mutableIntStateOf(1) }

    val competitor = competitorViewModel.competitorDetails
    competitorViewModel.getCompetitorDetails(idCompetitor)

    when(nextCompetitor){
        1 -> Column(modifier = Modifier.fillMaxSize().padding(25.dp)) {
            ListObstacles(obstacleViewModel, competitorViewModel ,numObstacle, idCourse, competitor) { nb -> setnbObstacles(nb) }
            Chronometre(numObstacle, nbObstacles, { num -> setNumObstacle(num) }, tempsObstacle, { temps -> setTempsObstacle(temps) },
                obstacleViewModel, idCourse, idCompetitor, viewModel, { next -> setNextCompetitor(next) }, { t -> setTime(t)})
        }
        0 -> vueNextCompetitor( { next -> setNextCompetitor(next) }, time)
    }
}

@Composable
fun ListObstacles(
    obstacleViewModel: ObstacleViewModel,
    competitorViewModel: CompetitorViewModel,
    numObstacle: Int,
    idCourse: Int,
    competitor: LiveData<NetworkResponse<CompetitorsItem>>,
    setNbObstacles: (Int) -> Unit,
){
    val obstacles = obstacleViewModel.courseObstacles.observeAsState()
    LaunchedEffect(Unit) {
        obstacleViewModel.getCourseObstacles(idCourse)
    }

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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

        when(val result = competitor.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                Text(text = result.data.first_name + result.data.last_name)
            }
            null -> {}
        }
    }
}

@Composable
fun afficheObstacleEnCours(data: ObstaclesCourse, numObstacle: Int) {
    Column {
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
    viewModel: ViewModelProvider,
    setNextCompetitor: (Int) -> Unit,
    setTimeChrono: (Int) -> Unit
) {

    var time by remember { mutableLongStateOf(0) }
    var tempsByObstacle by remember { mutableLongStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var startTime by remember { mutableLongStateOf(0) }
    var startTimeObstacle by remember { mutableLongStateOf(0) }
    var clickEnabled by remember { mutableStateOf(true) }

    val performanceObstacleViewModel = viewModel[PerformanceObstacleViewModel::class.java]
    val performanceViewModel = viewModel[PerformanceViewModel::class.java]

    val obstacles = obstacleViewModel.courseObstacles
    LaunchedEffect(Unit) {
        obstacleViewModel.getCourseObstacles(idCourse)
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
                        startTimeObstacle = System.currentTimeMillis() - tempsByObstacle
                        isRunning = true
                    }
                }, modifier = Modifier.weight(1f), enabled = clickEnabled
            ) {
                Text(text = if(isRunning) "Pause" else "Lancer", color = Color.White)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(onClick = {
                if(numObstacle == nbObstacles - 1){
                    Log.d("Dernier Temps", tempsObstacle.toString())
                    performanceObstacleViewModel.addObstacleParkour(PerformanceObstaclesItem(
                        id = -1,
                        obstacle_id = numObstacle,
                        performance_id = -1,
                        time = tempsByObstacle.toInt(),
                        has_fell = 0,
                        to_verify = 0,
                        created_at = "",
                        updated_at = ""
                    ))
                    clickEnabled = false
                    isRunning = false
                    performanceViewModel.createPerformance(PerformancesItem(
                        id = -1,
                        competitor_id = idCompetitor,
                        course_id = idCourse,
                        created_at = "",
                        updated_at = "",
                        access_token_id = -1,
                        status = "over",
                        total_time = time.toInt(),
                    ))
                    val performances =  performanceViewModel.performances.value
                    performanceViewModel.getPerformances()
                    for(o in performanceObstacleViewModel.listTimeByObstacle){
                        performanceObstacleViewModel.createPerformanceObstacle(PerformanceObstaclesItem(
                            id = -1,
                            obstacle_id = o.obstacle_id,
                            performance_id = if(performances is NetworkResponse.Success) performances.data[performances.data.size - 1].id else -1,
                            time = o.time,
                            has_fell = 0,
                            to_verify = 0,
                            created_at = "",
                            updated_at = ""
                        ))
                        Log.d("Resultat", o.time.toString())
                    }
                    Log.d("Temps obtenu", "Temps obtenu : $time")
                    setTimeChrono(time.toInt())
                    setNextCompetitor(0)
                    setNumObstacle(0)
                } else if(isRunning) {
                    // Sauvegarder le temps pour l'obstacle actuel avant de passer au suivant
                    performanceObstacleViewModel.addObstacleParkour(PerformanceObstaclesItem(
                        id = -1,
                        obstacle_id = numObstacle,
                        performance_id = -1,
                        time = tempsByObstacle.toInt(),
                        has_fell = 0,
                        to_verify = 0,
                        created_at = "",
                        updated_at = ""
                    ))
                    setNumObstacle(numObstacle + 1)

                    tempsByObstacle = 0
                    startTimeObstacle = System.currentTimeMillis()
                }
            }, modifier = Modifier.weight(1f)) {
                Text(text = if(numObstacle == nbObstacles - 1) "Fin de la course" else "Prochain Obstacle", color = Color.White)
            }
        }
    }

    LaunchedEffect(isRunning) {
        while(isRunning){
            delay(10)
            time = System.currentTimeMillis() - startTime
            tempsByObstacle = System.currentTimeMillis() - startTimeObstacle
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun formatTime(timeMiTotal: Long, timeMiObstacle: Long, setTempsObstacle: (Long) -> Unit): String{
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMiTotal) % 60
    val secondes = TimeUnit.MILLISECONDS.toSeconds(timeMiTotal) % 60

    // Utiliser directement le temps par obstacle pour le mettre à jour
    setTempsObstacle(timeMiObstacle)

    return String.format("%02d:%02d", minutes, secondes)
}
