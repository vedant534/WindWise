package com.example.windwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.windwise.navigation.WeatherNavigation
import com.example.windwise.ui.theme.WindWiseTheme
import dagger.hilt.android.AndroidEntryPoint

//let dagger know where are we using and connecting all dependencies
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindWiseTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(verticalArrangement =  Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        WeatherNavigation()

                    }

                }
            }
        }
    }
}

@Composable
fun WeatherApp (){

}