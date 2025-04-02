package com.example.androidprojetparkour.vue

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

//temps du competiteur
//si il a chuté ou pas
//id competiteur
@SuppressLint("DefaultLocale")
@Composable
fun vueNextCompetitor(setNextCompetitor: (Int) -> Unit, time: Int) {
    val formattedNumber = String.format("%.3f", time / 1000.0)
    println(formattedNumber)
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Row {
            Text(text = "Le compétiteur a fini la course en : $formattedNumber secondes")
        }
        Row {
            Button(
                onClick = { setNextCompetitor(1) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Passer au prochain compétiteur")
            }
        }
    }

}