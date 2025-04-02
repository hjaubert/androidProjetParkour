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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel

private val BackgroundColor = Color(0xFFF0F0F0)
private val BlueText = Color(0xFF3F51B5)
private val TimerGreen = Color(0xFF4CAF50)
private val PauseButtonColor = Color(0xFFE91E63)
private val NextButtonColor = Color(0xFF2196F3)
private val GoldButtonColor = Color(0xFFFFD700)

@Composable
fun vueListCompetition(viewModel: ViewModelProvider, navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
        val competitionsResult = viewModelCompetitions.competitions.observeAsState()

        LaunchedEffect(Unit) {
            viewModelCompetitions.getCompetitions()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titre en haut de la page avec le style de l'image
            Text(
                text = "LISTE DES COMPETITIONS",
                modifier = Modifier.padding(top = 30.dp, bottom = 16.dp),
                color = BlueText,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

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
                    ParkourDetails(data = result.data, navController)
                }
                null -> {}
            }
        }

        Button(
            onClick = { navController.navigate(Routes.vueNewCompetition) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 50.dp)
        ) {
            Text("New", fontSize = 25.sp)
        }
    }
}

@Composable
fun ParkourDetails(data: Competitions, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(data.toList()) { competition ->
            val showNewButtons = remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            showNewButtons.value = !showNewButtons.value
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = competition.name,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = BlueText
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Age : ${competition.age_min} - ${competition.age_max}",
                                fontSize = 16.sp
                            )

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Gender : ${if (competition.gender == "F") "Woman" else "Man"}",
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    text = "Has retry : ${if (competition.has_retry == 0) "No" else "Yes"}",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    sousBouton(showNewButtons, navController, competition)
                }
            }
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Button(
                onClick = { navController.navigate(Routes.vueListCompetitionsCompetitors + "/" + competition.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldButtonColor,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .width(250.dp)
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(" ➤ Competitors", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate(Routes.vueListCompetitionsParkours + "/" + competition.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldButtonColor,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .width(250.dp)
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(" ➤ Parkours", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
