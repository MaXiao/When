package com.xiaoism.time.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "people")
@Parcelize
data class Person(
    @PrimaryKey(autoGenerate = true)
    val personId: Long = 0,
    val name: String,
    val cityId: String
) : Parcelable