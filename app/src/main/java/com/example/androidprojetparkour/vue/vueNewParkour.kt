package com.example.androidprojetparkour.vue

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.androidprojetparkour.api.models.courses.CoursesItem
import com.example.androidprojetparkour.router.Routes
import com.example.androidprojetparkour.viewModel.CourseViewModel

@Composable
fun vueNewParkour(viewModel: ViewModelProvider, navController: NavHostController, data: Int) {
    var textName = remember { mutableStateOf("") };
    var textMaxDuration = remember { mutableStateOf("") };
    val context = LocalContext.current

    val viewModelCourse  = viewModel[CourseViewModel::class.java]
    val competitions = viewModelCourse.courses.observeAsState()


    Column(
        Modifier.fillMaxSize().padding(20.dp),horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(Modifier.padding(10.dp)) {
            Text("New Parkour",fontSize = 40.sp)
        }
        Row(Modifier.padding(10.dp)) {
            TextField(
                value = textName.value,
                onValueChange = {
                    textName.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Name :")},
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
                value = textMaxDuration.value,
                onValueChange = {
                    textMaxDuration.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Maximum duration :")},
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

        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = {

                    if(textName.value == ""){
                        Toast.makeText(context,"le nom et vide", Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(textMaxDuration.value == ""){
                        Toast.makeText(context,"Attention, le champ Durée maximale est vide", Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(!textMaxDuration.value.matches(Regex("^[0-9]+\$"))){
                        Toast.makeText(context,"Attention, le champ Durée maximale max n'est pas un nombre",Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(textMaxDuration.value.toInt() < 0){
                        Toast.makeText(context,"Attention, le champ Durée maximale doit étre supérieur à 0",Toast.LENGTH_SHORT).show();
                        return@Button
                    }
                    else if(textMaxDuration.value.toInt() > 10){
                        Toast.makeText(context,"Attention, le champ Durée maximale doit étre inférieur à 10",Toast.LENGTH_SHORT).show();
                        return@Button
                    }

                    viewModelCourse.createCourse(
                        CoursesItem(

                            competition_id = data,
                            created_at = "",
                            id = -1,
                            name = textName.value,
                            is_over = -1,
                            max_duration = textMaxDuration.value.toInt(),
                            position = -1,
                            updated_at = "",
                        )
                    )

                    navController.navigate(Routes.vueListCompetitionsParkours + "/" + data);
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp, bottom = 50.dp)
            ){
                Text("Validée")
            }

            Button(
                onClick = {
                    navController.navigate(Routes.vueListCompetitionsParkours + "/" + data);
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