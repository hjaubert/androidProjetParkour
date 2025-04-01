package com.example.androidprojetparkour.vue

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.androidprojetparkour.viewModel.CourseViewModel

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

    val viewModelCourse  = viewModel[CourseViewModel::class.java]
    val competitions = viewModelCourse.courses.observeAsState()
}
