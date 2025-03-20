package com.example.androidprojetparkour.api

import retrofit2.Response
import retrofit2.http.GET

interface ParkourApi {

    @GET("competitions")
    suspend fun getCompetitions() : Response<Competitions>

}