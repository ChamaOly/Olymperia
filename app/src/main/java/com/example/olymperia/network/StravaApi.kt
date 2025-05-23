package com.example.olymperia.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StravaApi {
    private const val BASE_URL = "https://www.strava.com/api/v3/"

    fun create(token: String): StravaApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(StravaApiService::class.java)
    }
}
