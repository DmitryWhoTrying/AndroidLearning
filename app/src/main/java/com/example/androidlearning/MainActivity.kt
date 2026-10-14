package com.example.androidlearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.androidlearning.ui.theme.VkEducationTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


val appCards = listOf(
    AppCardModel(
        name = "Гильдия Героев: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Гоблинов: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Гномов: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Эльфов: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Злодеев: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Разработчиков: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Тестировщиков: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Сисадминов: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Супергероев: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    AppCardModel(
        name = "Гильдия Удаленщиков: Экшен ММО РПГ",
        developer = "VK Play",
        category = "Игры",
        ageRating = 12,
        sizeMb = 224,
        description = "Легендарный рейд героев в Фэнтези РПГ. " +
                "Станьте героем гильдии и сразите мастера подземелья!")
    ,
    )
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkEducationTheme {
                AppRoot()
            }
        }
    }
}
@Composable
fun AppRoot() {
    // стек экранов: список id приложений
    val backStack = remember { mutableStateListOf<String>(appCards.first().name) }
    val currentName = backStack.last()
    val currentApp = appCards.first { it.name == currentName }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        AppDetailsScreen(
            app = currentApp,
            canGoBack = backStack.size > 1,
            onBackClick = {
                if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
            },
            onSimilarAppClick = { similar ->
                // не даём зайти на ту же карточку повторно
                if (similar.name != currentName) {
                    backStack.add(similar.name)
                }
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun AppDetailsScreen(
    app: AppCardModel,
    canGoBack: Boolean,
    onBackClick: () -> Unit,
    onSimilarAppClick: (AppCardModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Toolbar
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onBackClick,
                enabled = canGoBack,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = if (canGoBack) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline,
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconTest(
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(app.iconColor),
                app = app,
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = app.category,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = app.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(4.dp))
                Text(text = app.developer, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Row {
                    Column {
                        Text("${app.ageRating}+")
                        Spacer(Modifier.height(4.dp))
                        Text("Возраст", fontSize = 12.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("${app.sizeMb} MB")
                        Spacer(Modifier.height(4.dp))
                        Text("Размер", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {},
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text("Установить")
        }

        Spacer(Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Описание")
            Spacer(Modifier.height(8.dp))
            Text(
                text = app.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(Modifier.height(12.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(app.developer)
                Text("Разработчик", fontSize = 12.sp)
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
            }
        }

        Vitrine(
            modifier = Modifier.padding(start = 15.dp),
            curApp = app,
            onAppClick = onSimilarAppClick,
        )
    }
}


// Минимальная модель вместо domain.AppDetails
data class AppCardModel(
    val name: String,
    val developer: String,
    val category: String,
    val ageRating: Int,
    val sizeMb: Int,
    val description: String,
    val iconColor: Color = Color(0xFF4C8BF5),
)


@Composable
private fun IconTest(modifier:Modifier=Modifier, app: AppCardModel){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = app.name.firstOrNull()?.toString() ?: "?",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
        )
    }

}

@Composable
private fun Vitrine(modifier: Modifier= Modifier,
                    curApp : AppCardModel,
                    onAppClick: (AppCardModel) -> Unit){
    val apps:List<AppCardModel> = remember {
        appCards.filter { it != curApp }
                .shuffled()
                .take(9)
    }
    Column(modifier=modifier) {
        Text("Так же может понравиться")
        val state = rememberPagerState(0){3}
        HorizontalPager(state) { page ->
            Column() {
                AppMiniCard(apps[page*3], onClick = { onAppClick(apps[page*3]) })
                AppMiniCard(apps[page*3+1], onClick = { onAppClick(apps[page*3+1]) })
                AppMiniCard(apps[page*3+2], onClick = { onAppClick(apps[page*3+2])})
            }
        }
    }
}

@Composable
private fun AppMiniCard(
    app: AppCardModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconTest(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .padding(end = 15.dp),
            app = app,
        )
        Column(Modifier.padding(start = 15.dp)) {
            Text(app.name)
            Text(app.developer)
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun AppCardPreview() {
    MaterialTheme {
        AppDetailsScreen(appCards.first(), true, {}, { })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}