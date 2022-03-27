package com.xiaoism.time.model


import com.xiaoism.time.preview.CityData
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class CityTest {
    @Test
    fun getLocalTime() {
        val date = Date(2022, 1, 1, 9, 1)
        assertEquals(CityData.BEIJING.getLocalTimeFor(date), "22:01:00 / Feb 01")
    }
}