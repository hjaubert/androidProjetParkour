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
import androidx.compose.material3.Button
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
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel

@Composable
fun vueListConcurents(
    viewModel: ViewModelProvider,
    data: Int,
    navController: NavHostController,
    dataId: Int
) {
    val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
    val competitionsResult = viewModelCompetitions.registeredCompetitorsInCompetition.observeAsState()

    LaunchedEffect(Unit) {
        viewModelCompetitions.getRegisteredCompetitorsInCompetition(dataId)
    }
    Column {
        when(val result = competitionsResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                ListConcurent(data = result.data,navController,dataId, data)
            }
            null -> {}
        }

    }
}

@Composable
fun ListConcurent(data: Competitors, navController: NavHostController, competition: Int, idCourse: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("List of Comcurrents", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(25.dp))

            affichageListConcurent(data,navController, idCourse, competition)

        }   
    }
}

@Composable
fun affichageListConcurent(
    competitors: Competitors,
    navController: NavHostController,
    idCourse: Int,
    idCompetition: Int
) {

    LazyColumn {
        items(competitors.toList()) { competitors ->
            Button({
                navController.navigate(Routes.vueArbitrage + "/" + idCourse + "/" + competitors.id + "/" + idCompetition)
            }, modifier = Modifier.fillMaxWidth().padding(15.dp)) {
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
