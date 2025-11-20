package com.disah.yetanothernote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.disah.yetanothernote.navigation.NavGraph
import com.disah.yetanothernote.ui.theme.YetAnotherNoteTheme
import com.disah.yetanothernote.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YetAnotherNoteTheme {
                val navController = rememberNavController()
                val viewModel: NoteViewModel = viewModel()

                NavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}