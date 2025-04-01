package com.example.androidprojetparkour.vue

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
import com.example.androidprojetparkour.viewModel.CompetitorViewModel
import com.example.androidprojetparkour.viewModel.CourseViewModel

@Composable
fun vueListConcurents(
    viewModel: ViewModelProvider,
    data: Int,
    navController: NavHostController,
    dataId: Int
) {

    val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
    val competitionsResult = viewModelCompetitions.registeredCompetitorsInCompetition.observeAsState()

    val viewModelCours = viewModel[CourseViewModel::class.java]
    val competitorsResult = viewModelCours.competitorCourses.observeAsState()



    LaunchedEffect(Unit) {
        viewModelCompetitions.getRegisteredCompetitorsInCompetition(dataId)
    }
    Column {
        var listCompetitors = mutableListOf<CompetitorsItem>()
        when(val result = competitionsResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                Log.d("affichage des competitors", result.data.toString())
                for(competitor in result.data){

                    LaunchedEffect(Unit) {
                        viewModelCours.getCompetitorCourses(competitor.id)
                    }


                    when(val result1 = competitorsResult.value) {
                        is NetworkResponse.Error -> {
                            Text(text = result1.message)
                        }

                        NetworkResponse.Loading -> {
                            CircularProgressIndicator()
                        }

                        is NetworkResponse.Success -> {
                            for(cours in result1.data){

                                if(cours.id == data){
                                    Log.d("svsv", cours.id.toString() + " " + data)
                                    listCompetitors.add(competitor)
                                }
                            }
                        }

                        null -> {}
                    }

                }
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    for (elem in listCompetitors){
                        Text(elem.first_name + " " + elem.last_name)
                    }
                }


            }
            null -> {}
        }

    }
}