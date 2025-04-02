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
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel

@Composable
fun vueListCompetitionsCompetitors(
    viewModel: ViewModelProvider,
    competition: Int,
    navController: NavHostController
){

    val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
    val competitionsResult = viewModelCompetitions.registeredCompetitorsInCompetition.observeAsState()

    LaunchedEffect(Unit) {
        viewModelCompetitions.getRegisteredCompetitorsInCompetition(competition)
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
                listCompetitors(data = result.data,navController,competition)
            }
            null -> {}
        }

    }
}

@Composable
fun listCompetitors(data: Competitors, navController: NavHostController, competition: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("List of Competitors", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(25.dp))

            affichageListCompetitor(data,navController)

        }

        Button(
            onClick = { navController.navigate(Routes.vueListCompetitions) },
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

        Button(
            onClick = { navController.navigate(Routes.vueListCompetitionsCompetitorsAdd+"/"+ competition) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp,bottom = 50.dp)
        ) {
            Text("Add", fontSize = 25.sp)
        }
    }
}

@Composable
fun affichageListCompetitor(data: Competitors, navController: NavHostController) {
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
