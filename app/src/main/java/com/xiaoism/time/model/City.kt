package com.xiaoism.time.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "cities")
@Parcelize
data class City(
    @PrimaryKey @ColumnInfo(name = "Geoname ID") val geonameId: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "ASCII Name") val asciiName: String?,
    @ColumnInfo(name = "Timezone") val timezone: String,
    @ColumnInfo(name = "Longitude") val longitude: Double,
    @ColumnInfo(name = "Latitude") val latitude: Double,
    @ColumnInfo(name = "Country") val country: String,
    @ColumnInfo(name = "Alternate Names") val alternateNames: String?,
) : Parcelable {
    @Ignore
    val zone: TimeZone = TimeZone.getTimeZone(timezone)

    @delegate:Ignore
    private val dateFormat: SimpleDateFormat by lazy {
        val format = SimpleDateFormat("HH:mm:ss / MMM dd",
            Locale.getDefault())
        format.timeZone = zone
        format
    }

    fun getRawUTCOffset(): Int {
        return zone.rawOffset
    }

    fun getLocalOffset(date: Date): Int {
        return zone.getOffset(date.time)
    }

    fun getLocalTime(): String {
        return dateFormat.format(Date())
    }

    fun getLocalTimeFor(date: Date): String {
        return dateFormat.format(date)
    }
}