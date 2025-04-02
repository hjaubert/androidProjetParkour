package com.example.androidprojetparkour.vue

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
import com.example.androidprojetparkour.viewModel.CompetitorViewModel
import com.example.androidprojetparkour.viewModel.CourseViewModel
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter





@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun vueNewCompetitors(viewModel: ViewModelProvider, navController: NavHostController, data: Int) {
    var textFirstName = remember { mutableStateOf("") }
    var textLastName = remember { mutableStateOf("") }
    var textEmail = remember { mutableStateOf("") }
    var textPhone = remember { mutableStateOf("") }
    var gender = remember { mutableStateOf(false) }
    var textGender = remember { mutableStateOf("F") }
    var textBorn = remember { mutableStateOf("") }
    val context = LocalContext.current

    val viewModelCompetitors = viewModel[CompetitorViewModel::class.java]
    val competitions = viewModelCompetitors.competitors.observeAsState()

    val viewModelCompetitions = viewModel[CompetitionViewModel::class.java]
    val competition = viewModelCompetitions.oneCompetition.observeAsState()

    LaunchedEffect(Unit) {
        viewModelCompetitions.getOneCompetition(data)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header title
            Text(
                text = "NOUVEAU COMPÉTITEUR",
                color = primaryBlue,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center
            )

            // Main card containing the form
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
                    // Form title
                    Text(
                        text = "Informations personnelles",
                        fontSize = 22.sp,
                        color = primaryBlue,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // First Name Field
                    TextField(
                        value = textFirstName.value,
                        onValueChange = { textFirstName.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text("Prénom") },
                        leadingIcon = {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Person,
                                contentDescription = null,
                                tint = primaryBlue
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = primaryBlue,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = primaryBlue
                        )
                    )

                    // Last Name Field
                    TextField(
                        value = textLastName.value,
                        onValueChange = { textLastName.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text("Nom") },
                        leadingIcon = {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Person,
                                contentDescription = null,
                                tint = primaryBlue
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = primaryBlue,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = primaryBlue
                        )
                    )

                    // Email Field
                    TextField(
                        value = textEmail.value,
                        onValueChange = { textEmail.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Email,
                                contentDescription = null,
                                tint = primaryBlue
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = primaryBlue,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = primaryBlue
                        )
                    )

                    // Phone Field
                    TextField(
                        value = textPhone.value,
                        onValueChange = { textPhone.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text("Téléphone") },
                        leadingIcon = {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Phone,
                                contentDescription = null,
                                tint = primaryBlue
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = primaryBlue,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = primaryBlue
                        )
                    )

                    // Birth Date Field
                    TextField(
                        value = textBorn.value,
                        onValueChange = { textBorn.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text("Date de naissance (AAAA-MM-JJ)") },
                        leadingIcon = {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.AccountBox,
                                contentDescription = null,
                                tint = primaryBlue
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = primaryBlue,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = primaryBlue
                        )
                    )

                    // Gender Selection
                    Text(
                        text = "Genre",
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp),
                        textAlign = TextAlign.Start
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            RadioButton(
                                selected = gender.value,
                                onClick = { gender.value = true },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = primaryBlue
                                )
                            )
                            Text("Homme", color = Color.DarkGray)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            RadioButton(
                                selected = !gender.value,
                                onClick = { gender.value = false },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = primaryBlue
                                )
                            )
                            Text("Femme", color = Color.DarkGray)
                        }
                    }
                }
            }
        }

        // Bottom navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate(Routes.vueListCompetitionsCompetitorsAdd + "/" + data)
                },
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
                Text("ANNULER", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    Log.d("click", "click bouton")

                    if(textFirstName.value == "") {
                        Toast.makeText(context, "Attention, le champ prénom est vide", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(textLastName.value == "") {
                        Toast.makeText(context, "Attention, le champ nom est vide", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(!textEmail.value.matches(Regex("^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]+\$"))) {
                        Toast.makeText(context, "Attention, le champ email n'est pas valide", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(!textPhone.value.matches(Regex("^[0-9]+\$"))) {
                        Toast.makeText(context, "Attention, le champ numéro de téléphone n'est pas un nombre", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(textPhone.value.length != 10) {
                        Toast.makeText(context, "Attention, le champ numéro de téléphone doit contenir 10 chiffres", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(!textBorn.value.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                        Toast.makeText(context, "Attention, le champ née le est pas valide (AAAA-MM-JJ)", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    else if(LocalDate.parse(textBorn.value).isAfter(LocalDate.now())) {
                        Toast.makeText(context, "Attention, le champ née le est supérieur à la date actuelle", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val birthDate = LocalDate.parse(textBorn.value)
                    val age = Period.between(birthDate, LocalDate.now()).years

                    if(gender.value) {
                        textGender.value = "H"
                    } else {
                        textGender.value = "F"
                    }

                    val result = competition.value
                    if(result is NetworkResponse.Success) {
                        if(age < result.data.age_min) {
                            Toast.makeText(context, "Attention, l'age du competiteur est inférieur à l'age minimum de la compétition", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        else if(age > result.data.age_max) {
                            Toast.makeText(context, "Attention, l'age du competiteur est supérieur à l'age maximum de la compétition", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        else if(result.data.gender != textGender.value) {
                            Log.d("click", result.data.gender + ":" + textGender.value)
                            Toast.makeText(context, "Attention, le genre du competiteur est différent du genre de la compétition", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                    }

                    viewModelCompetitors.createCompetitor(
                        CompetitorsItem(
                            access_token_id = -1,
                            born_at = textBorn.value,
                            created_at = textBorn.value,
                            email = textEmail.value,
                            first_name = textFirstName.value,
                            gender = textGender.value,
                            id = -1,
                            last_name = textLastName.value,
                            phone = textPhone.value,
                            updated_at = ""
                        )
                    )

                    navController.navigate(Routes.vueListCompetitionsCompetitorsAdd + "/" + data)
                },
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
                Text("VALIDER", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}