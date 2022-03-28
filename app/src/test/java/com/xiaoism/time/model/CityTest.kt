package com.xiaoism.time.model


import com.xiaoism.time.preview.CityData
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.Calendar.*

class CityTest {
    private val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT")).apply {
        set(YEAR, 2022)
        set(MONTH, SEPTEMBER)
        set(DAY_OF_MONTH, 4)
        set(HOUR_OF_DAY, 13)
        set(MINUTE, 20)
        set(SECOND, 0)
    }

    @Test
    fun getLocalTime() {
        // Beijing is +8 and it doesn't have daylight saving
        assertEquals(CityData.BEIJING.getLocalTimeFor(cal.time), "21:20:00 / Sep 04")
    }

    @Test
    fun getLocalOffset() {
        // Beijing is +8
        assertEquals(CityData.BEIJING.getLocalOffset(cal.time), 8 * 60 * 60 * 1000)
    }
}