package com.example.androidprojetparkour.vue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.viewModel.CompetitionViewModel


@Composable
fun vueInfoCompetition(viewModel: ViewModelProvider, competition: Int){

    val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
    val competitionsResult = viewModelCompetitions.competitions.observeAsState()

    LaunchedEffect(Unit) {
        viewModelCompetitions.getOneCompetition(competition)
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
                affichageInfoCompetition(result)
            }
            null -> {}
        }

    }
}

@Composable
fun affichageInfoCompetition(result: NetworkResponse.Success<Competitions>) {
    val infoCompetion = result.data.get(1)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        Text("Information of Competition", fontSize = 40.sp)
        Text("Name : " + infoCompetion.name)
        Text("Age min : " + infoCompetion.age_min)
        Text("Age max : " + infoCompetion.age_max)
        Text("Gender : " + infoCompetion.gender)
        Text("Has retry : " + infoCompetion.has_retry)
        Button({}) {
            Text("Modify")
        }
    }
}


