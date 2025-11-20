package com.disah.yetanothernote.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.disah.yetanothernote.ui.screens.NoteDetailScreen
import com.disah.yetanothernote.ui.screens.NoteEditScreen
import com.disah.yetanothernote.ui.screens.NotesListScreen
import com.disah.yetanothernote.viewmodel.NoteViewModel

sealed class Screen(val route: String) {
    object NotesList : Screen("notes_list")
    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Long) = "note_detail/$noteId"
    }
    object NoteEdit : Screen("note_edit/{noteId}") {
        fun createRoute(noteId: Long) = "note_edit/$noteId"
    }
    object NoteAdd : Screen("note_add")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: NoteViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NotesList.route
    ) {
        composable(Screen.NotesList.route) {
            NotesListScreen(
                viewModel = viewModel,
                onNoteClick = { noteId ->
                    navController.navigate(Screen.NoteDetail.createRoute(noteId))
                },
                onAddNoteClick = {
                    navController.navigate(Screen.NoteAdd.route)
                }
            )
        }

        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: return@composable
            NoteDetailScreen(
                noteId = noteId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = { navController.navigate(Screen.NoteEdit.createRoute(noteId)) }
            )
        }

        composable(
            route = Screen.NoteEdit.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: return@composable
            NoteEditScreen(
                noteId = noteId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.NoteAdd.route) {
            NoteEditScreen(
                noteId = null,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
