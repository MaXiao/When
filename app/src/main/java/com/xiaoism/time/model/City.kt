package com.xiaoism.time.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Fts4
@Entity(tableName = "cities")
@Parcelize
data class City(
    @ColumnInfo(name = "Geoname ID") val geonameId: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "ASCII Name") val asciiName: String,
    @ColumnInfo(name = "Timezone") val timezone: String,
    @ColumnInfo(name = "Longitude") val longitude: Double,
    @ColumnInfo(name = "Latitude") val latitude: Double,
    @ColumnInfo(name = "Country") val country: String,
    @ColumnInfo(name = "Alternate Names") val alternateNames: String
) : Parcelable