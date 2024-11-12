package com.example.mipt_4_praktinis.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDAO: NoteDAO) {

    suspend fun add(note: NoteEntity) {
        noteDAO.add(note)
    }

    fun findAll(): Flow<List<NoteEntity>> {
        return noteDAO.findAll()
    }

    fun find(id: Long): Flow<NoteEntity> {
        return noteDAO.find(id)
    }

    suspend fun update(note: NoteEntity) {
        noteDAO.update(note)
    }

    suspend fun delete(note: NoteEntity) {
        noteDAO.delete(note)
    }
}