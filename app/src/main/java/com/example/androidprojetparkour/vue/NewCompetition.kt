package com.example.androidprojetparkour.vue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidprojetparkour.vue.ui.theme.AndroidProjetParkourTheme

class NewCompetition : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidProjetParkourTheme {
                var textName = remember { mutableStateOf("") }
                var textAgeMin = remember { mutableStateOf("") }
                var textAgeMax = remember { mutableStateOf("") }
                    Competitor(textName,textAgeMin,textAgeMax)
            }
        }
    }
}

@Composable
fun Competitor(textName: MutableState<String>,textAgeMin: MutableState<String>,textAgeMax: MutableState<String>) {
    Column(
        Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,
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
            RadioButton(false, onClick = {
                // à faire
            })
            Text("Man")

            RadioButton(false, onClick = {
                // à faire
            })
            Text("Woman")
        }

        Row (Modifier.padding(10.dp) , verticalAlignment = Alignment.CenterVertically){
            Text("Has retry :")
        }

        Row ( modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically){
            RadioButton(false, onClick = {
                // à faire
            })
            Text("True")

            RadioButton(false, onClick = {
                // à faire
            })
            Text("False")
        }

        Row {
            Button(
                onClick = {}
            ){
                Text("Cancel")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidProjetParkourTheme {
        var textName = remember { mutableStateOf("") }
        var textAgeMin = remember { mutableStateOf("") }
        var textAgeMax = remember { mutableStateOf("") }
        Competitor(textName,textAgeMin,textAgeMax)
    }
}