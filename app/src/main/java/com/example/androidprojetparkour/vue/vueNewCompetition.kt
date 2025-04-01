package com.example.androidprojetparkour.vue

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CompetitionViewModel
import kotlin.text.matches

@Composable
fun vueNewCompetition(viewModel: ViewModelProvider, navController: NavHostController) {
    var textName = remember { mutableStateOf("") };
    var textAgeMin = remember { mutableStateOf("") };
    var textAgeMax = remember { mutableStateOf("") };
    var gender = remember { mutableStateOf(false) };
    var textGender = remember { mutableStateOf("F") };
    var retry = remember { mutableStateOf(false) };
    var textRetry = remember { mutableIntStateOf(0) };
    val context = LocalContext.current

    val viewModelCompetition  = viewModel[CompetitionViewModel::class.java]
    val competitions = viewModelCompetition.competitions.observeAsState()

    Column(
        Modifier.fillMaxSize().padding(20.dp),horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row (Modifier.padding(10.dp)){
            Text("New Competition")
        }
        Row (Modifier.padding(10.dp)){
            Text("Name :")
            TextField(
                value = textName.value,
                onValueChange = {
                    textName.value = it
                },
            )
        }

        Row (Modifier.padding(10.dp)){
            Text("Age min :")
            TextField(
                value = textAgeMin.value,
                onValueChange = {
                    textAgeMin.value = it
                },
            )
        }

        Row (Modifier.padding(10.dp)){
            Text("Age max :")
            TextField(
                value = textAgeMax.value,
                onValueChange = {
                    textAgeMax.value = it
                },
            )
        }

        Row (Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){
            Text("gender :")
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

        Row (Modifier.padding(10.dp) , verticalAlignment = Alignment.CenterVertically){
            Text("Has retry :")
        }

        Row ( modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically){
            RadioButton(retry.value, onClick = {
                retry.value = !retry.value
            })
            Text("True")

            RadioButton(!retry.value, onClick = {
                retry.value = !retry.value
            })
            Text("False")
        }

        Row {
            Button(
                onClick = {

                    if(textName.value == ""){
                        Toast.makeText(context,"le nom et vide",Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(textAgeMin.value == ""){
                        Toast.makeText(context,"Attention, le champ Age min est vide",Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if (textAgeMax.value == ""){
                        Toast.makeText(context,"Attention, le champ Age max est vide",Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(!textAgeMin.value.matches(Regex("^[0-9]+\$"))){
                        Toast.makeText(context,"Attention, le champ Age min n'est pas un nombre",Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(!textAgeMax.value.matches(Regex("^[0-9]+\$"))){
                        Toast.makeText(context,"Attention, le champ Age max n'est pas un nombre",Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(textAgeMin.value.toInt() < 15){
                        Toast.makeText(context,"Attention, le champ Age min est inférieur à 15",Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(textAgeMax.value.toInt() > 99){
                        Toast.makeText(context,"Attention, le champ Age max doit etre inférieur à 99",Toast.LENGTH_SHORT).show();
                        return@Button
                    }

                    else if(textAgeMin.value.toInt() > textAgeMax.value.toInt()){
                        Toast.makeText(context,"Attention, le champ Age min et supérieur à age max",Toast.LENGTH_SHORT).show();
                        return@Button
                    }

                    if(gender.value){
                        textGender.value = "H"
                    }
                    else{
                        textGender.value = "F"

                    }

                    if(retry.value){
                        textRetry.value = 1
                    }
                    else{
                        textRetry.value = 0
                    }

                    viewModelCompetition.createCompetition(CompetitionsItem(
                        textAgeMax.value.toInt(),
                        age_min =  textAgeMin.value.toInt(),
                        created_at = "",
                        gender = textGender.value,
                        has_retry = textRetry.value,
                        id = -1,
                        name = textName.value,
                        status = "",
                        updated_at = "",
                    ))

                    navController.navigate(Routes.vueListCompetitions)
                }
            ){
                Text("Validée")
            }
        }

        Row {
            Button(
                onClick = {
                    navController.navigate(Routes.vueListCompetitions)
                }
            ){
                Text("Cancel")
            }
        }
    }
}