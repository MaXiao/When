package com.xiaoism.time.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonWithCity(
    @Embedded
    val person: Person,
    @Relation(parentColumn = "cityId", entityColumn = "Geoname ID")
    val city: City?
): Parcelable