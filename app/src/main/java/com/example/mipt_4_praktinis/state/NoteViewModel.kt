package com.example.mipt_4_praktinis.state


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mipt_4_praktinis.data.NoteEntity
import com.example.mipt_4_praktinis.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository = Graph.noteRepository) : ViewModel() {
    lateinit var findAll: Flow<List<NoteEntity>>

    init {
        viewModelScope.launch {
            findAll = noteRepository.findAll()
        }
    }

    fun add(note: NoteEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            noteRepository.add(note)
        }
    }

    fun find(id: Long): Flow<NoteEntity> {
        return noteRepository.find(id)
    }

    fun update(note: NoteEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            noteRepository.update(note)
        }
    }

    fun remove(note: NoteEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            noteRepository.delete(note)
            findAll = noteRepository.findAll()
        }
    }
}