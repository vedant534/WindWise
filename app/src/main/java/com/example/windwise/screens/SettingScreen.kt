package com.example.windwise.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.windwise.model.Unit
import com.example.windwise.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
                  ) {

    var unitToggleState by remember{
        mutableStateOf(false)
    }

    val mmU = listOf("Imperial (F)", " Metric (C)")

    val choiceFromDb = settingsViewModel.unitList.collectAsState().value

    val defChoice
    = if(choiceFromDb.isNullOrEmpty()) mmU[0] else choiceFromDb[0].unit

    var choiceState by remember {
        mutableStateOf(defChoice)
    }

    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "Settings",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                ){
                navController.popBackStack()
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally) {
                
                Text(text = "Change units of measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                    )

                IconToggleButton(checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        if(!unitToggleState) choiceState = mmU[0]
                        else choiceState = mmU[1]

                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {

                    Text(
                        text = if (unitToggleState) mmU[0] else mmU[1]
                    )
                    
                }
                
                Button(
                    onClick = {
                              settingsViewModel.deleteAllUnit()
                        settingsViewModel.insertUnit(Unit(unit = choiceState))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape =  RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                        )
                }
            }
        }
    }
}