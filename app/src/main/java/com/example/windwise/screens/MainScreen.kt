package com.example.windwise.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.windwise.data.DataOrException
import com.example.windwise.model.Weather
import com.example.windwise.model.WeatherItem
import com.example.windwise.navigation.WeatherScreens
import com.example.windwise.utils.formatDate
import com.example.windwise.utils.formatDecimals
import com.example.windwise.widgets.HumidityWindPressureRow
import com.example.windwise.widgets.SsRow
import com.example.windwise.widgets.WeatherAppBar
import com.example.windwise.widgets.WeatherDetailRow
import com.example.windwise.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
){

    val curCity: String =if (city!!.isBlank()) "Raipur" else city

    val unitFromDb = settingsViewModel.unitList.collectAsState().value

    var unit by remember {
        mutableStateOf("imoerial")
    }

    var isImperial by remember{
        mutableStateOf(false)
    }

    if(!unitFromDb.isNullOrEmpty()){
        unit =unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
        val weatherData =
            produceState<DataOrException<Weather, Boolean, Exception>>(
                initialValue = DataOrException(loading = true)
            ){
                value = mainViewModel.getWeatherData(
                    city = curCity,
                    units = unit
                    )
            }.value

        if(weatherData.loading == true){
            CircularProgressIndicator()
        }else if (weatherData.data!=null){
            MainScaffold(weather=weatherData.data!!, navController, isImperial = isImperial)
        }

    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    weather: Weather,
    navController: NavController,
    isImperial: Boolean
) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ",  ${weather.city.country}",
                icon = Icons.Default.ArrowBack,
                navController=navController,
                onAddActionClicked = {
                                     navController.navigate(WeatherScreens.SearchScreen.name)
                },
                elevation = 5.dp
            ){
                Log.d("TAG","MainScaffold: ButtonClicked")
            }
        }
    )
    {
        MainContent(data = weather,isImperial = isImperial)

    }

}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {

    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

    Column(
        modifier= Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = formatDate(weatherItem.dt),
            style=MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color= Color(0xFFFFC400)
        ){
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                WeatherStateImage(imageUrl)
                Text(text = formatDecimals(weatherItem.temp.day) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold)
                Text(text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic
                )

            }

        }

        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)
        Divider()
        SsRow(weather = weatherItem)
        Text(text="This Week",
            style=MaterialTheme.typography.h6,
            fontWeight = FontWeight.ExtraBold)
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color(0xFFEEF1EF),
            shape= RoundedCornerShape(14.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                items(items = data.list){item:WeatherItem->
                    WeatherDetailRow(item,isImperial)

                }

            }

        }


    }

}


