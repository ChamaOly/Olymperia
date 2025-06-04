package com.example.olymperia.network

import com.example.olymperia.model.Athlete
import com.example.olymperia.model.SegmentEffort
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StravaApiService {

    @GET("athlete")
    suspend fun getAthlete(
        @Header("Authorization") auth: String
    ): Athlete
    @GET("athlete/friends")
    suspend fun getAthleteFriends(
        @Header("Authorization") authHeader: String
    ): List<Athlete>


    @GET("segments/{segmentId}/all_efforts")
    suspend fun getSegmentEfforts(
        @Header("Authorization") auth: String,
        @Path("segmentId") segmentId: Long,
        @Header("AthleteId") athleteId: Long
    ): List<SegmentEffort>

    companion object {
        fun create(): StravaApiService {
            val client = okhttp3.OkHttpClient.Builder()
                .build()
            return Retrofit.Builder()
                .baseUrl("https://www.strava.com/api/v3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StravaApiService::class.java)
        }
    }
}
