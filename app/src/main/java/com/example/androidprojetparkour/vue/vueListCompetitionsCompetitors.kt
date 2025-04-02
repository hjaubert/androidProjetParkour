package com.example.androidprojetparkour.vue

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel


private val BackgroundColor = Color(0xFFF0F0F0)
private val BlueText = Color(0xFF3F51B5)
private val TimerGreen = Color(0xFF4CAF50)
private val PauseButtonColor = Color(0xFFE91E63)
private val NextButtonColor = Color(0xFF2196F3)
private val GoldButtonColor = Color(0xFFFFD700)

@Composable
fun vueListCompetitionsCompetitors(
    viewModel: ViewModelProvider,
    competition: Int,
    navController: NavHostController
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
        val competitionsResult = viewModelCompetitions.registeredCompetitorsInCompetition.observeAsState()

        LaunchedEffect(Unit) {
            viewModelCompetitions.getRegisteredCompetitorsInCompetition(competition)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(val result = competitionsResult.value){
                is NetworkResponse.Error -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = result.message,
                            modifier = Modifier.padding(16.dp),
                            color = Color.Red
                        )
                    }
                }
                NetworkResponse.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = BlueText
                        )
                    }
                }
                is NetworkResponse.Success -> {
                    listCompetitors(data = result.data, navController, competition)
                }
                null -> {}
            }
        }
    }
}

@Composable
fun listCompetitors(data: Competitors, navController: NavHostController, competition: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "LISTE DES PARTICIPANTS",
                modifier = Modifier.padding(bottom = 16.dp),
                color = BlueText,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            affichageListCompetitor(data, navController)
        }


        Button(
            onClick = { navController.navigate(Routes.vueListCompetitions) },
            colors = ButtonDefaults.buttonColors(
                containerColor = PauseButtonColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 50.dp)
        ) {
            Text("Retour", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }


        Button(
            onClick = { navController.navigate(Routes.vueListCompetitionsCompetitorsAdd+"/"+ competition) },
            colors = ButtonDefaults.buttonColors(
                containerColor = NextButtonColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 50.dp)
        ) {
            Text("Ajouter", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun affichageListCompetitor(data: Competitors, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(data.toList()) { competitors ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Button(
                    onClick = { /* Action lors du clic sur un compétiteur */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = competitors.first_name,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueText
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = competitors.last_name,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueText
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}