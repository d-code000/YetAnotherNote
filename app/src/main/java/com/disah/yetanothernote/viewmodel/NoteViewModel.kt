package com.disah.yetanothernote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.disah.yetanothernote.data.AppDatabase
import com.disah.yetanothernote.data.Note
import com.disah.yetanothernote.data.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    val allNotes: StateFlow<List<Note>>

    private val _selectedNotes = MutableStateFlow<Set<Long>>(emptySet())
    val selectedNotes: StateFlow<Set<Long>> = _selectedNotes.asStateFlow()

    private val _isMultiSelectMode = MutableStateFlow(false)
    val isMultiSelectMode: StateFlow<Boolean> = _isMultiSelectMode.asStateFlow()

    init {
        val noteDao = AppDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        allNotes = repository.allNotes
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun deleteSelectedNotes() = viewModelScope.launch {
        if (_selectedNotes.value.isNotEmpty()) {
            repository.deleteNotesByIds(_selectedNotes.value.toList())
            clearSelection()
        }
    }

    suspend fun getNoteById(noteId: Long): Note? {
        return repository.getNoteById(noteId)
    }

    fun toggleNoteSelection(noteId: Long) {
        _selectedNotes.value = if (_selectedNotes.value.contains(noteId)) {
            _selectedNotes.value - noteId
        } else {
            _selectedNotes.value + noteId
        }

        if (_selectedNotes.value.isEmpty()) {
            exitMultiSelectMode()
        }
    }

    fun enterMultiSelectMode() {
        _isMultiSelectMode.value = true
    }

    fun exitMultiSelectMode() {
        _isMultiSelectMode.value = false
        clearSelection()
    }

    private fun clearSelection() {
        _selectedNotes.value = emptySet()
    }

    fun isNoteSelected(noteId: Long): Boolean {
        return _selectedNotes.value.contains(noteId)
    }
}
