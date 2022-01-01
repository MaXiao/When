package com.xiaoism.time.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Group(
    @PrimaryKey(autoGenerate = true)
    val groupId: Long = 0,
    val name: String
) : Parcelable