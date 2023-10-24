package com.example.windwise.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.windwise.model.Favorite
import com.example.windwise.navigation.WeatherScreens
import com.example.windwise.screens.FavouriteViewModel

@Composable
fun WeatherAppBar(
    title: String = "title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
){

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val showDialog = remember {
        mutableStateOf(false)
    }

    if(showDialog.value){
        ShowSettingDropDownMenu(
            showDialog=showDialog,
            navController=navController
            )
    }

    TopAppBar(
        title = {
        Text(text = title,
            color = MaterialTheme.colors.onSecondary,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )
        },
        actions = {
                  if(isMainScreen){
                      IconButton(onClick = {
                          onAddActionClicked.invoke()
                        }
                      ) {
                          Icon(imageVector = Icons.Default.Search,
                              contentDescription = "search icon")
                          
                      }
                      IconButton(onClick = {
                          showDialog.value=true
                      }) {
                          Icon(imageVector = Icons.Rounded.MoreVert,
                              contentDescription = "more")

                      }
                  }
                  else Box{}



        },
        navigationIcon = {
            if(icon!=null){
                Icon(imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier =  Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }

            if (isMainScreen){
                val isAlreadyFavList =
                    favouriteViewModel
                    .favList
                    .collectAsState()
                    .value
                    .filter { item->
                        (item.city==title.split(",")[0])
                    }

                if(isAlreadyFavList.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "favicon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val cityList = title.split(",")

                                favouriteViewModel
                                    .insertFavorite(
                                        Favorite(
                                            city = cityList[0],
                                            country = cityList[1]
                                        )
                                    ).run {
                                        showIt.value = true
                                    }
                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                }else{
                    showIt.value = false
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null,
                        tint = Color.Red.copy(alpha = 0.6f))
                }

                ShowToast(context,showIt)
            }

        },
        backgroundColor = Color.Transparent,
        elevation = elevation)


}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if(showIt.value){
        Toast.makeText(context,"City Added to fav",Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {

    var expanded by remember {
        mutableStateOf(true)
    }

    var items = listOf(
        "About",
        "Favourites",
        "Settings"
        )

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(
            top = 45.dp,
            right = 20.dp
        )
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded=false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed {
                    index,
                    text -> DropdownMenuItem(onClick = {
                        expanded=false
                        showDialog.value=false
                        }
                    ) {
                        Icon(imageVector = when(text){
                            "About"->Icons.Default.Info
                            "Favourites"->Icons.Default.FavoriteBorder
                            else->Icons.Default.Settings
                            },
                            contentDescription = "icon 3 bindi",
                            tint = Color.LightGray
                        )
                        Text(text = text,
                            modifier = Modifier.clickable {
                                                          navController.navigate(
                                                              when(text){
                                                                  "About"-> WeatherScreens.AboutScreen.name
                                                                  "Favourites"-> WeatherScreens.FavouriteScreen.name
                                                                  else-> WeatherScreens.SettingScreen.name
                                                              }

                                                          )

                            },
                            fontWeight = FontWeight.W300
                            )



                }

            }

        }
    }
}
