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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
import androidx.compose.ui.graphics.Color
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.router.Routes


@Composable
fun vueListCompetition(viewModel: ViewModelProvider, navController: NavHostController){

    val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
    val competitionsResult = viewModelCompetitions.competitions.observeAsState()

    LaunchedEffect(Unit) {
        viewModelCompetitions.getCompetitions()
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
                ParkourDetails(data = result.data,navController)
            }
            null -> {}
        }

    }
}

@Composable
fun ParkourDetails(data: Competitions, navController: NavHostController){

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("List of Competition", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(25.dp))

            affichageCompetiton(data,navController)

        }

        Button(
            onClick = {
                navController.navigate(Routes.vueNewCompetition)
            },
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
fun affichageCompetiton(data: Competitions, navController: NavHostController) {
    LazyColumn {
        items(data.toList()) { competition ->
            var showNewButtons = remember { mutableStateOf(false) }
            Button({
                if (!showNewButtons.value){
                    showNewButtons.value = true
                }else {
                    showNewButtons.value = false
                }
            }, modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = competition.name,
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Age : " + competition.age_min + " - " + competition.age_max,
                        fontSize = 15.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Gender : " + if (competition.gender == "F") "Woman" else "Man",
                            fontSize = 15.sp,
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Has retry : " + if (competition.has_retry == 0) "No" else "Yes",
                            fontSize = 15.sp,
                        )
                    }
                }
            }

            sousBouton(showNewButtons,navController,competition)

        }
    }
}


@Composable
fun sousBouton(
    showNewButtons: MutableState<Boolean>,
    navController: NavHostController,
    competition: CompetitionsItem
) {
    if (showNewButtons.value) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {navController.navigate(Routes.vueListCompetitionsCompetitors + "/" + competition.id)},
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        ) {
            Text("Competitors", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {navController.navigate(Routes.vueListCompetitionsParkours + "/" + competition.id)},
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        ) {
            Text("Parkours", fontSize = 20.sp)
        }
    }
}