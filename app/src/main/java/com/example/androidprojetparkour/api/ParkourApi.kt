package com.example.androidprojetparkour.api

import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import com.example.androidprojetparkour.api.models.courses.Courses
import com.example.androidprojetparkour.api.models.courses.CoursesItem
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesCourse
import com.example.androidprojetparkour.api.models.obstacles.Obstacles
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesItem
import com.example.androidprojetparkour.api.models.performances.CompetitorPerformance
import com.example.androidprojetparkour.api.models.performances.Performances
import com.example.androidprojetparkour.api.models.performances.PerformancesItem
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstacles
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstaclesItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ParkourApi {

    /*
    #####################################################################################
    ############################### Methodes GET ########################################
    #####################################################################################
    */
    //-----------------------------Competitions------------------------------------------
    @GET("competitions")
    suspend fun getCompetitions() : Response<Competitions>

    @GET("competitions/{id}")
    suspend fun getOneCompetition(@Path("id") competitionId: Int): Response<CompetitionsItem>

    @GET("competitions/{id}/inscriptions")
    suspend fun getRegisteredCompetitorsInCompetition(@Path("id") competitionId: Int): Response<Competitors>

    @GET("competitions/{id}/courses")
    suspend fun getCompetitionCourses(@Path("id") competitionId: Int): Response<Courses>

    //-----------------------------Competitors-------------------------------------------
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

    //-----------------------------Courses-----------------------------------------------
    @GET("courses")
    suspend fun getCourses(): Response<Courses>

    @GET("courses/{id}/obstacles")
    suspend fun getObstacleInCourse(@Path("id") courseId: Int): Response<ObstaclesCourse>

    @GET("courses/{id}/performances")
    suspend fun getPerformancesCourse(@Path("id") courseId: Int): Response<Performances>

    @GET("courses/{id}")
    suspend fun getDetailsCourse(@Path("id") courseId: Int): Response<CoursesItem>

    @GET("courses/{id}/unused_obstacles")
    suspend fun getUnusedObstacle(@Path("id") courseId: Int): Response<Obstacles>

    //-----------------------------Obstacles---------------------------------------------
    @GET("obstacles")
    suspend fun getObstacles(): Response<Obstacles>

    @GET("obstacles/{id}")
    suspend fun getDetailsObstacle(@Path("id") obstacleId: Int): Response<ObstaclesItem>

    //-----------------------------PerformancesObstacle----------------------------------
    @GET("performance_obstacles")
    suspend fun getPerformancesByObstacle(): Response<PerformanceObstacles>

    @GET("performance_obstacles/{id}")
    suspend fun getDetailsPerformanceObstacle(@Path("id") performanceObstacleId: Int): Response<PerformanceObstaclesItem>

    //-----------------------------Performances------------------------------------------
    @GET("performances")
    suspend fun getPerformances(): Response<Performances>

    @GET("performances/{id}")
    suspend fun getInformationPerformance(@Path("id") performanceId: Int): Response<PerformancesItem>

    @GET("performances/{id}/details")
    suspend fun getDetailsPerformance(@Path("id") performanceId: Int): Response<PerformanceObstacles>

    /*
    #####################################################################################
    ############################### Methodes POST ########################################
    #####################################################################################
    */

    //-----------------------------Competitions------------------------------------------
    @POST("competitions")
    suspend fun storeCompetition(@Body competition: CompetitionsItem): Response<CompetitionsItem>

    @POST("competitions/{id}/add_competitor")
    suspend fun addCompetitorToCompetition(@Path("id") competitionId: Int, @Body competitorId: Int): Response<CompetitorsItem>

    //-----------------------------Competitors-------------------------------------------
    @POST("competitors")
    suspend fun storeCompetitor(@Body competitor: CompetitorsItem): Response<CompetitorsItem>

    //-----------------------------Courses-----------------------------------------------
    @POST("courses")
    suspend fun storeCourse(@Body course: CoursesItem): Response<CoursesItem>

    @POST("courses/{id}/add_obstacle")
    suspend fun addObstacleToCourse(@Path("id") courseId: Int, @Body obstacleId: Int): Response<ObstaclesItem>

    @POST("courses/{id}/update_obtacle_position")
    suspend fun updateObstaclePosition(@Path("id") courseId: Int, @Body obstacleId: Int, @Body position: Int): Response<ObstaclesItem>

    //-----------------------------Obstacles---------------------------------------------
    @POST("obstacles")
    suspend fun storeObstacle(@Body obstacle: ObstaclesItem): Response<ObstaclesItem>

    //-----------------------------PerformancesObstacle----------------------------------
    @POST("performance_obstacles")
    suspend fun storePerformanceObstacle(@Body performanceObstacle: PerformanceObstaclesItem): Response<PerformanceObstaclesItem>

    //-----------------------------Performances------------------------------------------
    @POST("performances")
    suspend fun storePerformance(@Body performance: PerformancesItem): Response<PerformancesItem>

    /*
    #####################################################################################
    ############################### Methodes PUT ########################################
    #####################################################################################
    */

    //-----------------------------Competitions------------------------------------------
    @PUT("competitions/{id}")
    suspend fun updateCompetition(@Path("id") competitionId: Int, @Body competition: CompetitionsItem): Response<CompetitionsItem>

    //-----------------------------Competitors-------------------------------------------
    @PUT("competitors/{id}")
    suspend fun updateCompetitor(@Path("id") competitorId: Int, @Body competitor: CompetitorsItem): Response<CompetitorsItem>

    //-----------------------------Courses-----------------------------------------------
    @PUT("courses/{id}")
    suspend fun updateCourse(@Path("id") courseId: Int, @Body course: CoursesItem): Response<CoursesItem>
    //-----------------------------Obstacles---------------------------------------------
    @PUT("obstacles/{id}")
    suspend fun updateObstacle(@Path("id") obstacleId: Int, @Body obstacle: ObstaclesItem): Response<ObstaclesItem>

    //-----------------------------PerformancesObstacle----------------------------------
    @PUT("performance_obstacles/{id}")
    suspend fun updatePerformanceObstacle(@Path("id") performanceObstacleId: Int, @Body performanceObstacle: PerformanceObstaclesItem): Response<PerformanceObstaclesItem>

    //-----------------------------Performances------------------------------------------
    @PUT("performances/{id}")
    suspend fun updatePerformance(@Path("id") performanceId: Int, @Body performance: PerformancesItem): Response<PerformancesItem>

    /*
    #####################################################################################
    ############################### Methodes DELETE ########################################
    #####################################################################################
    */

    //-----------------------------Competitions------------------------------------------
    @DELETE("competitions/{id}")
    suspend fun deleteCompetition(@Path("id") competitionId: Int): Response<Unit>

    @DELETE("competitions/{id}/remove_competitor/{id_competitor}")
    suspend fun removeCompetitorFromCompetition(@Path("id") competitionId: Int, @Path("id_competitor") competitorId: Int): Response<Unit>

    //-----------------------------Competitors-------------------------------------------
    @DELETE("competitors/{id}")
    suspend fun deleteCompetitor(@Path("id") competitorId: Int): Response<Unit>

    //-----------------------------Courses-----------------------------------------------
    @DELETE("courses/{id}")
    suspend fun deleteCourse(@Path("id") courseId: Int): Response<Unit>

    @DELETE("courses/{id}/remove_obstacle/{id_obstacle}")
    suspend fun removeObstacleFromCourse(@Path("id") courseId: Int, @Path("id_obstacle") obstacleId: Int): Response<Unit>

    //-----------------------------Obstacles---------------------------------------------
    @DELETE("obstacles/{id}")
    suspend fun deleteObstacle(@Path("id") obstacleId: Int): Response<Unit>

    //-----------------------------Performances------------------------------------------
    @DELETE("performances/{id}")
    suspend fun deletePerformance(@Path("id") performanceId: Int): Response<Unit>

}