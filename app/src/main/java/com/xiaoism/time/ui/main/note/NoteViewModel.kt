package com.xiaoism.time.ui.main.note

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.Note
import com.xiaoism.time.repository.NoteRepository
import com.xiaoism.time.repository.TimeDatabase

class NoteViewModel @ViewModelInject constructor(application: Application) :
    AndroidViewModel(application) {
    private val database = TimeDatabase.getDatabase(application, viewModelScope)
    private val repository = NoteRepository(database.noteDao)
    private val allNotes: LiveData<List<Note>> = repository.getAll()

    fun create(note: Note) {
        repository.create(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}