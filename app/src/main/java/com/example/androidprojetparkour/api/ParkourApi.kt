package com.example.androidprojetparkour.api

import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.api.models.courses.Courses
import com.example.androidprojetparkour.api.models.courses.CoursesItem
import com.example.androidprojetparkour.api.models.obstacles.Obstacles
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesItem
import com.example.androidprojetparkour.api.models.performances.CompetitorPerformance
import com.example.androidprojetparkour.api.models.performances.Performances
import com.example.androidprojetparkour.api.models.performances.PerformancesItem
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstacles
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstaclesItem
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

    @GET("competitors/{id}/courses")
    suspend fun getCoursesCompetitorIsIn(@Path("id") competitorId: Int): Response<Courses>

    @GET("competitors/{id}/{id_course}/details_performances")
    suspend fun getCompetitorPerformancesInCourse(@Path("id") competitorId: Int, @Path("id_course") courseId: Int): Response<CompetitorPerformance>

    @GET("courses")
    suspend fun getCourses(): Response<Courses>

    @GET("courses/{id}/obstacles")
    suspend fun getObstacleInCourse(@Path("id") courseId: Int): Response<Obstacles>

    @GET("courses/{id}/performances")
    suspend fun getPerformancesCourse(@Path("id") courseId: Int): Response<Performances>

    @GET("courses/{id}")
    suspend fun getDetailsCourse(@Path("id") courseId: Int): Response<CoursesItem>

    @GET("obstacles")
    suspend fun getObstacles(): Response<Obstacles>

    @GET("obstacles/{id}")
    suspend fun getDetailsObstacle(@Path("id") obstacleId: Int): Response<ObstaclesItem>

    @GET("performance_obstacles")
    suspend fun getPerformancesByObstacle(): Response<PerformanceObstacles>

    @GET("performance_obstacles/{id}")
    suspend fun getDetailsPerformanceObstacle(@Path("id") performanceObstacleId: Int): Response<PerformanceObstaclesItem>

    @GET("performances")
    suspend fun getPerformances(): Response<Performances>

    @GET("performances/{id}")
    suspend fun getInformationPerformance(@Path("id") performanceId: Int): Response<PerformancesItem>

    @GET("performances/{id}/details")
    suspend fun getDetailsPerformance(@Path("id") performanceId: Int): Response<PerformanceObstacles>

}