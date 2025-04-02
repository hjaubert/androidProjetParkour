package com.example.androidprojetparkour.vue

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitions.CompetitorInCompetitionPost
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
import com.example.androidprojetparkour.viewModel.CompetitorViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Define UI colors to match the image style
val primaryBlue = Color(0xFF4052B6)
val accentGreen = Color(0xFF4CB050)
val pauseRed = Color(0xFFE91E63)
val suivantBlue = Color(0xFF2196F3)
val lightGray = Color(0xFFF5F5F5)

@Composable
fun vueListCompetitionsCompetitorsAdd(
    viewModel: ViewModelProvider,
    competition: Int,
    navController: NavHostController
){
    val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
    val viewModelCompetitiors = viewModel[CompetitorViewModel::class.java]

    val competitionsResult = viewModelCompetitions.competitions.observeAsState()
    val competitorsRegisteredResult = viewModelCompetitions.registeredCompetitorsInCompetition.observeAsState()
    val competitorsResult = viewModelCompetitiors.competitors.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        LaunchedEffect(Unit) {
            viewModelCompetitions.getOneCompetition(competition)
            viewModelCompetitions.getRegisteredCompetitorsInCompetition(competition)
            viewModelCompetitiors.getCompetitors()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header like "OBSTACLE EN COURS"
            Text(
                text = "COMPÉTITEURS VALIDES",
                color = primaryBlue,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center
            )

            when(val resultCompetition = competitionsResult.value){
                is NetworkResponse.Error -> {
                    ErrorCard(resultCompetition.message)
                }
                NetworkResponse.Loading -> {
                    LoadingIndicator()
                }
                is NetworkResponse.Success -> {
                    when(val resultCompetitorRegister = competitorsRegisteredResult.value){
                        is NetworkResponse.Error -> {
                            ErrorCard(resultCompetitorRegister.message)
                        }
                        NetworkResponse.Loading -> {
                            LoadingIndicator()
                        }
                        is NetworkResponse.Success -> {
                            when(val competitorsResult = competitorsResult.value){
                                is NetworkResponse.Error -> {
                                    ErrorCard(competitorsResult.message)
                                }
                                NetworkResponse.Loading -> {
                                    LoadingIndicator()
                                }
                                is NetworkResponse.Success -> {
                                    val competitorsValide = listCompetitiorsValide(resultCompetition.data, resultCompetitorRegister.data, competitorsResult.data)
                                    listCompetitorsAdd(competitorsValide, navController, competition, viewModelCompetitions, competition)
                                }
                                null -> {}
                            }
                        }
                        null -> {}
                    }
                }
                null -> {}
            }
        }
    }
}

@Composable
fun ErrorCard(message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = message,
            color = Color.Red,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = primaryBlue,
            modifier = Modifier.width(64.dp).height(64.dp)
        )
    }
}

@Composable
fun listCompetitiorsValide(theCompetition: Competitions, CompetitorsInCompetition: Competitors, AllCompetitors: Competitors): List<CompetitorsItem> {
    val competitoesValide = mutableListOf<CompetitorsItem>()
    for (competitorAssume in AllCompetitors){
        if (competitorAssume.gender == theCompetition[0].gender && calculateAge(competitorAssume.born_at) >= theCompetition[0].age_min && calculateAge(competitorAssume.born_at) <= theCompetition[0].age_max && !CompetitorsInCompetition.contains(competitorAssume)){
            competitoesValide.add(competitorAssume)
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
        age--
    }

    return age
}

@Composable
fun listCompetitorsAdd(
    data: List<CompetitorsItem>,
    navController: NavHostController,
    competition: Int,
    viewModelCompetitions: CompetitionViewModel,
    competition1: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main card like in the image
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Liste des compétiteurs",
                    fontSize = 28.sp,
                    color = primaryBlue,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "${data.size} valides",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // List of competitors
                affichageListCompetitorAdd(data, navController, viewModelCompetitions, competition)
            }
        }

        // Bottom navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate(Routes.vueListCompetitionsParkours + "/" + competition) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = pauseRed,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(56.dp)
            ) {
                Text("RETOUR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { navController.navigate(Routes.vueNewCompetitors+"/"+competition) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = suivantBlue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(56.dp)
            ) {
                Text("NOUVEAU", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun affichageListCompetitorAdd(
    data: List<CompetitorsItem>,
    navController: NavHostController,
    viewModelCompetitions: CompetitionViewModel,
    competition: Int
) {

    val competitorsResult = viewModelCompetitions.addCompetitorResult.observeAsState()
    val context = LocalContext.current

    if (data.isEmpty()) {
        Text(
            text = "Aucun compétiteur valide trouvé",
            fontSize = 18.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(32.dp)
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(data.toList()) { competitor ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Button(
                        onClick = {
                            val competitorInCompetitionPost = CompetitorInCompetitionPost(competitor.id)
                            viewModelCompetitions.addCompetitorToCompetition(competition, competitorInCompetitionPost)
                            navController.navigate(Routes.vueListCompetitionsCompetitorsAdd+"/"+ competition)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ){
                            Text(
                                text = competitor.first_name,
                                fontSize = 20.sp,
                                color = primaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = competitor.last_name,
                                fontSize = 20.sp,
                                color = primaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}