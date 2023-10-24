package com.example.windwise.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.windwise.R
import com.example.windwise.model.WeatherItem
import com.example.windwise.utils.formatDate
import com.example.windwise.utils.formatDateTime
import com.example.windwise.utils.formatDecimals

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){

        Row (modifier = Modifier.padding(4.dp)
        ){

            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.humidity}%",
                style= MaterialTheme.typography.caption)

        }

        Row (modifier = Modifier.padding(4.dp)
        ){

            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "psi icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.pressure} psi",
                style= MaterialTheme.typography.caption)

        }

        Row (modifier = Modifier.padding(4.dp)
        ){

            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${formatDecimals(weather.speed)}" +  if(isImperial) "mph" else "m/s",
                style= MaterialTheme.typography.caption)

        }
    }

}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Icon Image",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun SsRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.padding(4.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "rise icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDateTime(weather.sunrise)}".uppercase(),
                style = MaterialTheme.typography.caption
            )

        }

        Row(
            modifier = Modifier.padding(4.dp)
        ) {

            Text(
                text = "${formatDateTime(weather.sunset)}".uppercase(),
                style = MaterialTheme.typography.caption
            )

            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "set icon",
                modifier = Modifier.size(20.dp)
            )


        }

    }
}

@Composable
fun WeatherDetailRow(weather: WeatherItem, isImperial: Boolean) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape= CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text= formatDate(weather.dt).split(",")[0],
                modifier = Modifier.padding(5.dp)
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(Modifier.padding(0.dp),
                CircleShape,
                Color(0xFFFFC400)
            ) {
                Text(weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)

            }

            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ){
                    append(formatDecimals(weather.temp.max) +   if(isImperial) "°C" else "°F")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray,
                    )
                ){
                    append(formatDecimals(weather.temp.min) + if(isImperial) "°C" else "°F")
                }

            }
            )

        }

    }

}

