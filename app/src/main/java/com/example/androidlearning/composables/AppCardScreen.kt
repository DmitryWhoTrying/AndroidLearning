package com.example.androidlearning.composables

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.androidlearning.Domain.AppData
import com.example.androidlearning.Presentation.AppDescription
import com.example.androidlearning.Presentation.AppDataViewModel
import com.example.androidlearning.Presentation.AppDetailsState
import com.example.androidlearning.Presentation.InstallState

@Composable
fun AppCardScreen(
    viewModel: AppDataViewModel,
    modifier: Modifier = Modifier,
    onInstallClick: () -> Unit = { viewModel.onInstallClick() },
    onBackClick: (() -> Unit)? = { viewModel.onBackClick() }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val s = state) {
        is AppDetailsState.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        is AppDetailsState.Content -> {
            AppCardContent(
                content = s,
                onInstallClick = onInstallClick,
                onBackClick = onBackClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun AppCardContent(
    content: AppDetailsState.Content,
    onInstallClick: () -> Unit,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val appData = content.selectedApp ?: content.appData.firstOrNull() ?: return
    var descriptionCollapsed by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Toolbar(onBackClick = onBackClick)
        Spacer(Modifier.height(8.dp))

        AppDetailsHeader(
            appData = appData,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))

        InstallButton(
            installState = content.installState,
            onClick = onInstallClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(12.dp))

        val screenshots = appData.screenshotUrlList ?: appData.screenshots
        if (!screenshots.isNullOrEmpty()) {
            ScreenshotsList(
                screenshotUrlList = screenshots,
                contentPadding = PaddingValues(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
        }

        appData.appDescription?.let { description ->
            AppDescription(
                description = description,
                collapsed = descriptionCollapsed,
                onReadMoreClick = { descriptionCollapsed = !descriptionCollapsed },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        } else {
            Spacer(Modifier.size(48.dp))
        }

        if (onShareClick != null) {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share"
                )
            }
        } else {
            Spacer(Modifier.size(48.dp))
        }
    }
}

@Composable
fun AppDetailsHeader(
    appData: AppData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = appData.imagePath,
            contentDescription = appData.appName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = appData.appName ?: "Без названия",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = appData.appGenre ?: "Другое",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row{
                Text(text="рейтинг: ", style = MaterialTheme.typography.bodySmall)
                Text(text=appData.rating?.toString() ?: "без рейтинга", style = MaterialTheme.typography.bodySmall)
            }

            val infoText = listOfNotNull(
                appData.appAgeRating?.let { "$it+" },
                appData.appSize?.let { "$it MB" }
            ).joinToString(" • ")
            if (infoText.isNotEmpty()) {
                Text(
                    text = infoText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun InstallButton(
    installState: InstallState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (installState) {
        is InstallState.NotInstalled -> {
            Button(
                onClick = onClick,
                modifier = modifier
            ) {
                Text(text = "Установить")
            }
        }
        is InstallState.Installing -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )
                    Text(text = "Установка ${installState.progress}%")
                }
            }
        }
        is InstallState.Installed -> {
            Button(
                onClick = onClick,
                modifier = modifier
            ) {
                Text(text = "Установлено")
            }
        }
    }
}

@Composable
fun ScreenshotsList(
    screenshotUrlList: List<String>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(screenshotUrlList) { url ->
            AsyncImage(
                model = url,
                contentDescription = "Screenshot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(200.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
