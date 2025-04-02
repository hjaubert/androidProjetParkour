package com.example.androidprojetparkour.vue

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel

@Composable
fun vueNewCompetition(viewModel: ViewModelProvider, navController: NavHostController) {
    var textName = remember { mutableStateOf("") }
    var textAgeMin = remember { mutableStateOf("") }
    var textAgeMax = remember { mutableStateOf("") }
    var gender = remember { mutableStateOf(false) }
    var textGender = remember { mutableStateOf("F") }
    var retry = remember { mutableStateOf(false) }
    var textRetry = remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    val viewModelCompetition = viewModel[CompetitionViewModel::class.java]
    val competitions = viewModelCompetition.competitions.observeAsState()

    val blueHeader = Color(0xFF4A5FBA)
    val cardBackground = Color.White
    val buttonPause = Color(0xFFE83A7D)
    val buttonSuivant = Color(0xFF2196F3)
    val timerGreen = Color(0xFF4CAF50)
    val backgroundColor = Color(0xFFF2F2F2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(blueHeader)
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = "Nouvelle Compétition",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackground)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "information",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = blueHeader,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextField(
                    value = textName.value,
                    onValueChange = { textName.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    label = { Text("Nom de la compétition") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                    )
                )

                TextField(
                    value = textAgeMin.value,
                    onValueChange = { textAgeMin.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    label = { Text("Âge minimum") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                    )
                )

                TextField(
                    value = textAgeMax.value,
                    onValueChange = { textAgeMax.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    label = { Text("Âge maximum") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                    )
                )

                Text(
                    text = "Genre:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    textAlign = TextAlign.Start
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = gender.value,
                        onClick = {
                            gender.value = true
                            textGender.value = "M"
                        }
                    )
                    Text("Homme")

                    Spacer(modifier = Modifier.weight(1f))

                    RadioButton(
                        selected = !gender.value,
                        onClick = {
                            gender.value = false
                            textGender.value = "F"
                        }
                    )
                    Text("Femme")
                }

                Text(
                    text = "Permission de réessayer:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    textAlign = TextAlign.Start
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = retry.value,
                        onClick = {
                            retry.value = true
                            textRetry.value = 1
                        }
                    )
                    Text("Oui")

                    Spacer(modifier = Modifier.weight(1f))

                    RadioButton(
                        selected = !retry.value,
                        onClick = {
                            retry.value = false
                            textRetry.value = 0
                        }
                    )
                    Text("Non")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate(Routes.vueListCompetitions)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonPause)
            ) {
                Text(
                    "ANNULER",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    if (textName.value == "") {
                        Toast.makeText(context, "Le nom est vide", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else if (textAgeMin.value == "") {
                        Toast.makeText(context, "Attention, le champ Âge min est vide", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else if (textAgeMax.value == "") {
                        Toast.makeText(context, "Attention, le champ Âge max est vide", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else if (!textAgeMin.value.matches(Regex("^[0-9]+\$"))) {
                        Toast.makeText(context, "Attention, le champ Âge min n'est pas un nombre", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else if (!textAgeMax.value.matches(Regex("^[0-9]+\$"))) {
                        Toast.makeText(context, "Attention, le champ Âge max n'est pas un nombre", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else if (textAgeMin.value.toInt() < 15) {
                        Toast.makeText(context, "Attention, le champ Âge min est inférieur à 15", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else if (textAgeMax.value.toInt() > 99) {
                        Toast.makeText(context, "Attention, le champ Âge max doit être inférieur à 99", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else if (textAgeMin.value.toInt() > textAgeMax.value.toInt()) {
                        Toast.makeText(context, "Attention, le champ Âge min est supérieur à Âge max", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    viewModelCompetition.createCompetition(
                        CompetitionsItem(
                            textAgeMax.value.toInt(),
                            age_min = textAgeMin.value.toInt(),
                            created_at = "",
                            gender = textGender.value,
                            has_retry = textRetry.value,
                            id = -1,
                            name = textName.value,
                            status = "",
                            updated_at = ""
                        )
                    )

                    navController.navigate(Routes.vueListCompetitions)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonSuivant)
            ) {
                Text(
                    "VALIDER",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}