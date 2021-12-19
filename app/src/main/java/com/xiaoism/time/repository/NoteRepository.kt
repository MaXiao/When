package com.xiaoism.time.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.xiaoism.time.model.Note

class NoteRepository(private val noteDao: NoteDao) {
    private val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun create(note: Note) {
        InsertNoteAsyncTask(
            noteDao
        ).execute(note)
    }

    fun getAll(): LiveData<List<Note>> {
        Log.d("note", noteDao.toString())
        return allNotes
    }

    private class InsertNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {

        override fun doInBackground(vararg note: Note?) {
            Log.d("note insert", noteDao.toString())
            noteDao.insert(note[0]!!)
        }
    }
}