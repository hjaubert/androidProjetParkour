package com.example.androidprojetparkour.vue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.androidprojetparkour.api.models.courses.Courses
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CourseViewModel

@Composable
fun vueListCompetitionsParkours(
    viewModel: ViewModelProvider,
    competition: Int,
    navController: NavHostController
){
    val viewModelCompetitions = viewModel[CourseViewModel::class.java]
    val competitionsResult = viewModelCompetitions.competitionCourses.observeAsState()

    LaunchedEffect(Unit) {
        viewModelCompetitions.getCompetitionCourses(competition)
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
                listParkour(data = result.data,navController,competition)
            }
            null -> {}
        }

    }
}

@Composable
fun listParkour(data: Courses, navController: NavHostController, competition: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Spacer(modifier = Modifier.height(35.dp))
            Text("List of Parkours", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(25.dp))

            affichageListParkours(data,navController,competition)

        }

        Button(
            onClick = { navController.navigate(Routes.vueNewParkour + "/" + competition) },
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
fun affichageListParkours(data: Courses, navController: NavHostController, competition: Int) {
    LazyColumn {
        items(data.toList()) { course ->
            Button({ navController.navigate(Routes.vueListConcurents+"/" + course.id + "/" + competition) }, modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = course.name,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}