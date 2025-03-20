package com.example.androidprojetparkour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.ui.theme.AndroidProjetParkourTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parkourViewModel = ViewModelProvider(this)[ParkourViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            AndroidProjetParkourTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(20.dp)
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
    Column {
        IconButton(onClick = {
            viewModel.getData()
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
                Text(text = result.data.toString())
            }
            null -> {}
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