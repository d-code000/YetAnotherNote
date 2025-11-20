package com.disah.yetanothernote.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun getNoteById(noteId: Long): Note? {
        return noteDao.getNoteById(noteId)
    }

    suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun deleteNotesByIds(noteIds: List<Long>) {
        noteDao.deleteNotesByIds(noteIds)
    }

    suspend fun getNotesCount(): Int {
        return noteDao.getNotesCount()
    }
}
