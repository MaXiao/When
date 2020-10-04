package com.xiaoism.time.ui.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_table")
data class TimeEntity(@PrimaryKey @ColumnInfo(name = "text") val text: String)