package com.example.androidprojetparkour.vue

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesCourse
import com.example.androidprojetparkour.api.models.performances.PerformancesItem
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstaclesItem
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
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
    idCompetition: Int
) {

    val obstacleViewModel = viewModel[ObstacleViewModel::class.java]
    val competitorViewModel = viewModel[CompetitorViewModel::class.java]
    val competitionViewModel = viewModel[CompetitionViewModel::class.java]

    var (numObstacle, setNumObstacle) = remember { mutableIntStateOf(1) }
    var (nbObstacles, setnbObstacles) = remember { mutableIntStateOf(0) }
    var (tempsObstacle, setTempsObstacle) = remember { mutableLongStateOf(0) }
    var (time, setTime) = remember { mutableIntStateOf(0) }
    var (numCompetitor, setNumCompetitor) = remember { mutableIntStateOf(0) }
    var (nextCompetitor, setNextCompetitor) = remember { mutableIntStateOf(1) }


    val concurrents = competitionViewModel.registeredCompetitorsInCompetition.value
    LaunchedEffect(concurrents) {
        competitionViewModel.getRegisteredCompetitorsInCompetition(idCompetition)
    }

    val idCompetitor = if(concurrents is NetworkResponse.Success) concurrents.data[numCompetitor].id else -1
    //println(id)

    when(nextCompetitor){
        1 -> Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                ListObstacles(obstacleViewModel, competitorViewModel, numObstacle, idCourse, idCompetitor) { nb -> setnbObstacles(nb) }
            }

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Chronometre(numObstacle, nbObstacles, { num -> setNumObstacle(num) }, tempsObstacle, { temps -> setTempsObstacle(temps) },
                    obstacleViewModel, idCourse, idCompetitor, viewModel, { next -> setNextCompetitor(next) }, { t -> setTime(t)})
            }
        }
        0 -> vueNextCompetitor( { next -> setNextCompetitor(next) }, time, { num -> setNumCompetitor(num) }, numCompetitor)
    }
}

@Composable
fun ListObstacles(
    obstacleViewModel: ObstacleViewModel,
    competitorViewModel: CompetitorViewModel,
    numObstacle: Int,
    idCourse: Int,
    idCompetitor: Int,
    setNbObstacles: (Int) -> Unit,
){
    val obstacles = obstacleViewModel.courseObstacles.observeAsState()
    val competitor = competitorViewModel.competitorDetails

    LaunchedEffect(obstacles, competitor) {
        obstacleViewModel.getCourseObstacles(idCourse)
        competitorViewModel.getCompetitorDetails(idCompetitor)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "OBSTACLE EN COURS",
            modifier = Modifier.padding(bottom = 24.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3F51B5)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when(val result = obstacles.value){
                    is NetworkResponse.Error -> {
                        Text(
                            text = result.message,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                    NetworkResponse.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(20.dp),
                            color = Color(0xFF3F51B5)
                        )
                    }
                    is NetworkResponse.Success -> {
                        setNbObstacles(result.data.size)
                        afficheObstacleEnCours(result.data, numObstacle)
                    }
                    null -> {}
                }

                Spacer(modifier = Modifier.height(20.dp))

                when(val result = competitor.value){
                    is NetworkResponse.Error -> {
                        Text(
                            text = result.message,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                    NetworkResponse.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(10.dp),
                            color = Color(0xFF3F51B5)
                        )
                    }
                    is NetworkResponse.Success -> {
                        Text(
                            text = "Participant:",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "${result.data.first_name} ${result.data.last_name}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    null -> {}
                }
            }
        }
    }
}

@Composable
fun afficheObstacleEnCours(data: ObstaclesCourse, numObstacle: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Obstacle #${numObstacle}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = data[numObstacle].obstacle_name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3F51B5),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Progression: ${numObstacle}/${data.size - 1}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "CHRONOMÈTRE",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = formatTime(time, tempsByObstacle, setTempsObstacle),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = if(isRunning) Color(0xFF4CAF50) else Color(0xFF3F51B5),
                    modifier = Modifier.padding(vertical = 24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if(isRunning){
                                isRunning = false
                            } else {
                                startTime = System.currentTimeMillis() - time
                                startTimeObstacle = System.currentTimeMillis() - tempsByObstacle
                                isRunning = true
                            }
                        },
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                        enabled = clickEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(isRunning) Color(0xFFE91E63) else Color(0xFF4CAF50),
                            disabledContainerColor = Color.Gray
                        )
                    ) {
                        Text(
                            text = if(isRunning) "PAUSE" else "LANCER",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
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
                                    //Log.d("Resultat", o.time.toString())
                                }
                                //Log.d("Temps obtenu", "Temps obtenu : $time")
                                setTimeChrono(time.toInt())
                                setNextCompetitor(0)
                                setNumObstacle(1)
                            } else if(isRunning) {
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
                        },
                        modifier = Modifier.weight(1f).padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(numObstacle == nbObstacles - 1) Color(0xFFFF9800) else Color(0xFF2196F3),
                            disabledContainerColor = Color.Gray
                        ),
                        enabled = isRunning || numObstacle == nbObstacles - 1
                    ) {
                        Text(
                            text = if(numObstacle == nbObstacles - 1) "TERMINER" else "SUIVANT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
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
    val millis = (timeMiTotal / 10) % 100
    setTempsObstacle(timeMiObstacle)

    return String.format("%02d:%02d.%02d", minutes, secondes, millis)
}