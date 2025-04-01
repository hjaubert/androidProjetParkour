package com.example.androidprojetparkour.vue

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
            Text("New Parkour")
        }
        Row(Modifier.padding(10.dp)) {
            Text("Name :")
            TextField(
                value = textName.value,
                onValueChange = {
                    textName.value = it
                },
            )
        }
        Row (Modifier.padding(10.dp)){
            Text("Durée maximale :")
            TextField(
                value = textMaxDuration.value,
                onValueChange = {
                    textMaxDuration.value = it
                },
            )
        }

        Row {
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
                }
            ){
                Text("Validée")
            }
        }

        Row {
            Button(
                onClick = {
                    navController.navigate(Routes.vueListCompetitionsParkours + "/" + data);
                }
            ){
                Text("Cancel")
            }
        }
    }
}