package com.xiaoism.time.preview

import com.xiaoism.time.model.City

sealed class CityData {
    companion object {
        val TORONTO = City(
            geonameId = "10103951",
            name = "Toronto",
            timezone = "America/Toronto",
            longitude = -79.41,
            latitude = 43.7,
            country = "Canada",
            alternateNames = null,
            asciiName = null
        )
        val BEIJING = City(
            geonameId = "1816670",
            name = "Beijing",
            timezone = "Asia/Shanghai",
            longitude = 116.3972,
            latitude = 39.9075,
            country = "China",
            alternateNames = null,
            asciiName = null
        )
    }
}
