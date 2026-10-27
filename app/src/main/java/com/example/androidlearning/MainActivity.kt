package com.example.androidlearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidlearning.ui.theme.Pink80
import com.example.androidlearning.ui.theme.VkEducationTheme
import coil3.compose.AsyncImage


//hardcoded data
//https://picsum.photos/200/200 - random images
@Composable
fun AppScreen(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: AppDataViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is ScreenEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    when (val s = state) {
        is AppDetailsState.Loading -> LoadingScreen(modifier = modifier)
        is AppDetailsState.Content -> AppListScreen(
            appList = s.appData,
            onToolbarClick = { viewModel.processToolbarClick() },
            modifier = modifier
        )
    }
}

@Composable
fun AppListScreen(
    appList: List<AppData>,
    onToolbarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        ToolBar(onToolbarClick = onToolbarClick)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            appList.forEach {
                AppCard(
                    modifier = Modifier.padding(vertical = 10.dp),
                    appData = it
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkEducationTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    AppScreen(
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun ToolBar(
    onToolbarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Pink80)
            .clickable { onToolbarClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.icon),
            contentDescription = "Icon",
            Modifier
                .size(width = 50.dp, height = 50.dp)
                .padding(0.dp)
        )

        Text("One more text")
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