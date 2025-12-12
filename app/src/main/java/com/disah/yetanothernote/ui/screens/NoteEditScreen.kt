package com.disah.yetanothernote.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.disah.yetanothernote.data.Note
import com.disah.yetanothernote.utils.LocationHelper
import com.disah.yetanothernote.viewmodel.NoteViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NoteEditScreen(
    noteId: Long?,
    viewModel: NoteViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(noteId != null) }
    var isLoadingLocation by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locationHelper = remember { LocationHelper(context) }

    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(noteId) {
        if (noteId != null) {
            viewModel.getNoteById(noteId)?.let { note ->
                title = note.title
                content = note.content
                latitude = note.latitude
                longitude = note.longitude
            }
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Новая заметка" else "Редактирование") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank()) {
                                val note = if (noteId == null) {
                                    Note(
                                        title = title,
                                        content = content,
                                        latitude = latitude,
                                        longitude = longitude
                                    )
                                } else {
                                    Note(
                                        id = noteId,
                                        title = title,
                                        content = content,
                                        latitude = latitude,
                                        longitude = longitude
                                    )
                                }

                                if (noteId == null) {
                                    viewModel.insertNote(note)
                                } else {
                                    viewModel.updateNote(note)
                                }
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Сохранить")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Заголовок") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Содержание") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                minLines = 10
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (latitude != null && longitude != null) {
                Text(
                    text = "Геопозиция: $latitude, $longitude",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            OutlinedButton(
                onClick = {
                    if (locationPermissionsState.allPermissionsGranted) {
                        scope.launch {
                            isLoadingLocation = true
                            locationError = null
                            when (val result = locationHelper.getCurrentLocation(context)) {
                                is LocationHelper.LocationResult.Success -> {
                                    latitude = result.latitude
                                    longitude = result.longitude
                                    locationError = null
                                }
                                is LocationHelper.LocationResult.Error -> {
                                    locationError = result.message
                                }
                            }
                            isLoadingLocation = false
                        }
                    } else {
                        locationPermissionsState.launchMultiplePermissionRequest()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoadingLocation
            ) {
                if (isLoadingLocation) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    when {
                        isLoadingLocation -> "Получение координат..."
                        locationPermissionsState.allPermissionsGranted -> "Добавить текущую геопозицию"
                        else -> "Запросить доступ к геопозиции"
                    }
                )
            }

            if (locationError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = locationError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
