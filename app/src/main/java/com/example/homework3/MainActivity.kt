package com.example.homework3

import android.graphics.fonts.FontStyle
import android.icu.text.ListFormatter
import android.os.Bundle
import android.renderscript.Allocation
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.homework3.ui.theme.AppData
import com.example.homework3.ui.theme.Homework3Theme
import com.example.homework3.ui.theme.Pink80

//hardcoded data
//https://picsum.photos/200/200 - random images
val AppList = listOf(
    AppData("Game 1", "Strategy", "Description 1, pretty small", "https://picsum.photos/200/200"),
    AppData("Game 2", "Economical", "Description 2, pretty big pretty" +
            " big pretty big pretty " + " big pretty big pretty " +
            "big pretty big pretty big", "https://picsum.photos/200/200"),
    AppData("Game 3", "Adventure", "Decription 3", "https://picsum.photos/200/200"),
    AppData("Game 4", "Action", "Description 4", "https://picsum.photos/200/200"),
    AppData("Game 5", "Strategy", null, "https://picsum.photos/200/200"),
    AppData("Game 6", null, "Description 6", "https://picsum.photos/200/200"),
    AppData("Game 7", "Strategy", "Description 7", "https://picsum.photos/200/200"),
    AppData("Game 8", "Strategy", "Description 8", "https://picsum.photos/200/200")
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Homework3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun ToolBar(modifier: Modifier = Modifier){
    Row(modifier=modifier
        .fillMaxWidth()
        .background(Pink80),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Image(painterResource(
            R.drawable.icon),
            contentDescription = "Icon",
            Modifier
                .size(width = 50.dp, height = 50.dp)
                .padding(0.dp))

        Text("One more text")
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier= modifier) {
        ToolBar()

        Column(modifier= Modifier
            .padding(start = 10.dp)
            .verticalScroll(rememberScrollState())
            ) {
            AppList.forEach {
                AppCard(
                    modifier =
                        Modifier.padding(vertical = 10.dp),
                    appData = it
                )
            }
        }
    }
}

@Composable
fun AppCard(modifier: Modifier = Modifier,
            appData: AppData){
    Row(modifier= modifier){

        AsyncImage(
            model = appData.imagePath,
            contentDescription =null,
            modifier= Modifier
            .padding(top=3.dp)
            .width(95.dp)
            .height(95.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Pink80)
        )
        AppInfo(modifier = Modifier.padding(start = 5.dp),appData=appData)
    }
}

@Composable
fun AppInfo(modifier: Modifier= Modifier,
            appData: AppData){

    Column(modifier=modifier){
        Text(appData.appName, fontWeight = FontWeight.Bold)
        Text(appData.appGenre ?: "Другое", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
        Text(appData.appDescription ?: "Без описания", maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
    
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Homework3Theme {
        Column {
            Greeting("Android")
        }
    }
}