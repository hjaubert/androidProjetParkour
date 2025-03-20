package com.example.androidprojetparkour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.Competitions
import com.example.androidprojetparkour.ui.theme.AndroidProjetParkourTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parkourViewModel = ViewModelProvider(this)[ParkourViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            AndroidProjetParkourTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(20.dp).background(Color.White)
                ) {
                    ParkourPage(parkourViewModel)
                }
            }
        }
    }
}

@Composable
fun ParkourPage(viewModel: ParkourViewModel){
    val competitionsResult = viewModel.parkourResult.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getData()
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
                ParkourDetails(data = result.data)
            }
            null -> {}
        }

    }
}

@Composable
fun ParkourDetails(data : Competitions){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Text("List of Competition", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(25.dp))
            for(competition in data){
                Text(text = competition.name, fontSize = 30.sp, modifier = Modifier.padding(10.dp))
            }
        }

        Button(
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("New")
        }
    }
}