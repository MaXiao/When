package com.xiaoism.time.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    val title: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}