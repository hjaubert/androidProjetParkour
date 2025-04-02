package com.example.androidprojetparkour.vue

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
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
    var textFirstName = remember { mutableStateOf("") };
    var textLastName = remember { mutableStateOf("") };
    var textEmail = remember { mutableStateOf("") };
    var textPhone = remember { mutableStateOf("") };
    var gender = remember { mutableStateOf(false) };
    var textGender = remember { mutableStateOf("F") };
    var textBorn = remember { mutableStateOf("") };
    val context = LocalContext.current

    val viewModelCompetitors  = viewModel[CompetitorViewModel::class.java]
    val competitions = viewModelCompetitors.competitors.observeAsState()


    val viewModelCompetitions  = viewModel[CompetitionViewModel::class.java]
    val competition = viewModelCompetitions.oneCompetition.observeAsState()
    LaunchedEffect(Unit) {
        viewModelCompetitions.getOneCompetition(data)
    }


    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row (Modifier.padding(10.dp)){
            Text("New Competition",fontSize = 40.sp)
        }
        Row (Modifier.padding(10.dp)){
            TextField(
                value = textFirstName.value,
                onValueChange = {
                    textFirstName.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("First name :")},
                leadingIcon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Person,
                        contentDescription = null,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFA500),
                    unfocusedContainerColor = Color(0xFFFFA500),
                )
            )
        }
        Row (Modifier.padding(10.dp)){
            TextField(
                value = textLastName.value,
                onValueChange = {
                    textLastName.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Last name :")},
                leadingIcon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Person,
                        contentDescription = null,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Green,
                    unfocusedContainerColor = Color.Green,
                )
            )
        }
        Row (Modifier.padding(10.dp)){
            TextField(
                value = textEmail.value,
                onValueChange = {
                    textEmail.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Email :") },
                leadingIcon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Email,
                        contentDescription = null,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Cyan,
                    unfocusedContainerColor = Color.Cyan,
                )
            )
        }
        Row (Modifier.padding(10.dp)){
            TextField(
                value = textPhone.value,
                onValueChange = {
                    textPhone.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Phone number :") },
                leadingIcon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Phone,
                        contentDescription = null,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFF6F61),
                    unfocusedContainerColor = Color(0xFFFF6F61),
                )
            )
        }
        Row (Modifier.padding(10.dp)){
            TextField(
                value = textBorn.value,
                onValueChange = {
                    textBorn.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Born on :") },
                leadingIcon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.AccountBox,
                        contentDescription = null,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Yellow,
                    unfocusedContainerColor = Color.Yellow,
                )
            )
        }
        Row (Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){
            Text("gender :",fontSize =20.sp)
        }

        Row ( modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically){
            RadioButton(gender.value, onClick = {
                gender.value = !gender.value;
            })
            Text("Man")

            RadioButton(!gender.value, onClick = {
                gender.value = !gender.value;
            })
            Text("Woman")
        }

        val result = competition.value

        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                    onClick = {

                        Log.d("click", "click bouton")


                        if(textFirstName.value == ""){
                            Toast.makeText(context,"Attention, le champ prénom est vide",Toast.LENGTH_SHORT).show();
                            return@Button
                        }
                        else if(textLastName.value == ""){
                            Toast.makeText(context,"Attention, le champ nom est vide",Toast.LENGTH_SHORT).show();
                            return@Button
                        }
                        else if(!textEmail.value.matches(Regex("^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]+\$"))){
                            Toast.makeText(context,"Attention, le champ email n'est pas valide",Toast.LENGTH_SHORT).show();
                            return@Button
                        }
                        else if(!textPhone.value.matches(Regex("^[0-9]+\$"))){
                            Toast.makeText(context,"Attention, le champ numéro de téléphone n'est pas un nombre",Toast.LENGTH_SHORT).show();
                            return@Button
                        }
                        else if(textPhone.value.length != 10){
                            Toast.makeText(context,"Attention, le champ numéro de téléphone doit contenir 10 chiffres",Toast.LENGTH_SHORT).show();
                            return@Button
                        }
                        else if(!textBorn.value.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))){
                            Toast.makeText(context,"Attention, le champ née le est pas valide (AAAA-MM-JJ)",Toast.LENGTH_SHORT).show();
                            return@Button
                        }
                        else if(LocalDate.parse(textBorn.value).isAfter(LocalDate.now())){
                            Toast.makeText(context,"Attention, le champ née le est supérieur à la date actuelle",Toast.LENGTH_SHORT).show();
                            return@Button
                        }

                        val birthDate: LocalDate

                        birthDate = LocalDate.parse(textBorn.value)

                        val age = Period.between(birthDate, LocalDate.now()).years

                        if(gender.value){
                            textGender.value = "H"
                        }
                        else{
                            textGender.value = "F"

                        }


                        if(result is NetworkResponse.Success) {
                            if(age < result.data.age_min ){
                                Toast.makeText(context,"Attention, l'age du competiteur est inférieur à l'age minimum de la compétition",Toast.LENGTH_SHORT).show();
                                return@Button
                            }
                            else if (age > result.data.age_max){
                                Toast.makeText(context,"Attention, l'age du competiteur est supérieur à l'age maximum de la compétition",Toast.LENGTH_SHORT).show();
                                return@Button
                            }
                            else if(result.data.gender != textGender.value){
                                Log.d("click", result.data.gender + ":" + textGender.value)
                                Toast.makeText(context,"Attention, le genre du competiteur est différent du genre de la compétition",Toast.LENGTH_SHORT).show();
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
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp, bottom = 50.dp)
                ){
                    Text("Validée")
                }


                Button(
                    onClick = {
                        navController.navigate(Routes.vueListCompetitionsCompetitorsAdd + "/" + data)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp, bottom = 50.dp)
                ){
                    Text("Cancel")
                }
            }
    }
}
