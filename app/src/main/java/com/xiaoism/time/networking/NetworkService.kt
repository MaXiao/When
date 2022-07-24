package com.xiaoism.time.networking

import com.xiaoism.time.model.remote.SunsetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SunsetService {
    @GET(NetworkConstants.PATH)
    suspend fun getSunTime(
        @Query("lat") latitude: Float,
        @Query("lng") longitude: Float
    ): SunsetResponse
}