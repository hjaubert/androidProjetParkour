package com.example.androidprojetparkour.vue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun vueClassement(viewModel: ViewModelProvider, navController: NavHostController, data: Int){
    val viewModelclassement = viewModel[CompetitionViewModel::class.java] //pas sur look !!!
    val competitionsResult = viewModelclassement.competitions.observeAsState() //pas sur look !!!

    LaunchedEffect(Unit) {
        viewModelclassement.getCompetitions()
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
                affichageClassement(data = result.data,navController)
            }
            null -> {}
        }

    }

}

@Composable
fun affichageClassement(data: Competitions, navController: NavHostController) {
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
                        text = competition.name, //mettre la possition + ":" + prénom + nom + temps ;
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

            sousBouton(showNewButtons,navController,competition)

        }
    }
}