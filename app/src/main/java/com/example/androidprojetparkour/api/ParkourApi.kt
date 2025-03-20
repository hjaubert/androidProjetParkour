package com.example.androidprojetparkour.api

import com.example.androidprojetparkour.api.models.Competitions
import retrofit2.Response
import retrofit2.http.GET

interface ParkourApi {

    @GET("competitions")
    suspend fun getCompetitions() : Response<Competitions>

}