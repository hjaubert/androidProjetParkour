package com.example.androidprojetparkour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.ui.theme.AndroidProjetParkourTheme
import com.example.androidprojetparkour.viewModel.CompetitorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parkourViewModel = ViewModelProvider(this)[CompetitorViewModel::class.java]

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
fun ParkourPage(viewModel: CompetitorViewModel){
    val competitionsResult = viewModel.oneCompetitor.observeAsState()
    Column {
        IconButton(onClick = {
            viewModel.getOneCompetitor(211)
        }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }

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
fun ParkourDetails(data : CompetitorsItem){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Compétitions", fontSize = 30.sp,
            )
            /*Row {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "personne",
                    modifier = Modifier.size(40.dp)
                )
                Text(text = data.competitor.first_name + " " + data.competitor.last_name, fontSize = 30.sp)
            }*/
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "personne",
                    modifier = Modifier.size(40.dp)
                )
                Text(text = data.first_name + " " + data.last_name)
            }

        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidProjetParkourTheme {
        ParkourPage()
    }
}*/