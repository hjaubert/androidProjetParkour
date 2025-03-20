package com.example.androidprojetparkour.api

import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.api.models.courses.Courses
import com.example.androidprojetparkour.api.models.performances.CompetitorPerformance
import com.example.androidprojetparkour.api.models.performances.Performances
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ParkourApi {

    @GET("competitions")
    suspend fun getCompetitions() : Response<Competitions>

    @GET("competitions/{id}")
    suspend fun getOneCompetition(@Path("id") competitionId: Int): Response<CompetitionsItem>

    @GET("competitions/{id}/inscriptions")
    suspend fun getRegisteredCompetitorsInCompetition(@Path("id") competitionId: Int): Response<Competitors>

    @GET("competitions/{id}/courses")
    suspend fun getCompetitionCourses(@Path("id") competitionId: Int): Response<Courses>

    @GET("competitors")
    suspend fun getCompetitors() : Response<Competitors>

    @GET("competitors/{id}")
    suspend fun getCompetitorsDetail(@Path("id") competitorId: Int): Response<CompetitorsItem>

    @GET("competitors/{id}/performances")
    suspend fun getPerformancesCompetitor(@Path("id") competitorId: Int): Response<CompetitorPerformance>

}