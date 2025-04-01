package com.example.androidprojetparkour.vue


import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun vueListCompetitionsCompetitorsAdd(
    viewModel: ViewModelProvider,
    competition: Int,
    navController: NavHostController
){

    val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]

    val competitionsResult = viewModelCompetitions.competitions.observeAsState()
    val competitorsRegisteredResult = viewModelCompetitions.registeredCompetitorsInCompetition.observeAsState()



    LaunchedEffect(Unit) {
        viewModelCompetitions.getCompetitions()
        viewModelCompetitions.getRegisteredCompetitorsInCompetition(competition)
    }
    Column {
        when(val resultCompetition = competitionsResult.value){
            is NetworkResponse.Error -> {
                Text(text = resultCompetition.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                when(val resultCompetitorRegister = competitorsRegisteredResult.value){
                    is NetworkResponse.Error -> {
                        Text(text = resultCompetitorRegister.message)
                    }
                    NetworkResponse.Loading -> {
                        CircularProgressIndicator()
                    }
                    is NetworkResponse.Success -> {
                        val competitorsValide = listCompetitiorsValide(resultCompetition.data,resultCompetitorRegister.data)
                        listCompetitorsAdd(competitorsValide,navController,competition)
                    }
                    null -> {}
                }
            }
            null -> {}
        }

    }
}


@Composable
fun listCompetitiorsValide(data: Competitions, data1: Competitors): List<CompetitorsItem> {
    val competitoesValide = mutableListOf<CompetitorsItem>()
    for (competitor in data1){
        if (competitor.gender == data[0].gender && calculateAge(competitor.born_at) >= data[0].age_min && calculateAge(competitor.born_at) <= data[0].age_max ){

            competitoesValide.add(competitor)
        }
    }
    return competitoesValide.toList()
}


fun calculateAge(birthDate: String): Int {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val birthDateObj = sdf.parse(birthDate) ?: return -1

    val birthCalendar = Calendar.getInstance().apply { time = birthDateObj }
    val todayCalendar = Calendar.getInstance()

    var age = todayCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

    if (todayCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
        age-- // Ajuste si l'anniversaire n'est pas encore passé cette année
    }

    return age
}

@Composable
fun listCompetitorsAdd(data: List<CompetitorsItem>, navController: NavHostController, competition: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("List of Competitors", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(25.dp))

            affichageListCompetitorAdd(data,navController)

        }

        Button(
            onClick = { navController.navigate(Routes.vueListCompetitionsCompetitorsAdd+"/"+competition) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("New", fontSize = 25.sp)
        }
    }
}

@Composable
fun affichageListCompetitorAdd(data: List<CompetitorsItem>, navController: NavHostController) {
    LazyColumn {
        items(data.toList()) { competitors ->
            Button({ }, modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = competitors.first_name,
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = competitors.last_name,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}
