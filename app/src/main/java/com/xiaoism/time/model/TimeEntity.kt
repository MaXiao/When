package com.xiaoism.time.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time")
data class TimeEntity(@PrimaryKey val text: String)