package com.example.androidprojetparkour.bdd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidprojetparkour.bdd.models.PerformanceObstacleBdd
import com.example.androidprojetparkour.bdd.models.PerformancesBdd
import kotlinx.coroutines.flow.Flow

@Dao
interface PerformancesDAO {

    @Insert
    suspend fun insertPerformance(performance: PerformancesBdd)

    @Insert
    suspend fun insertPerformanceObstacle(performanceObstacle: PerformanceObstacleBdd)

    @Update
    suspend fun updatePerformance(performance: PerformancesBdd)

    @Query("SELECT * FROM PerformancesBdd WHERE course_id = :idCourse AND competitor_id = :idCompetitor")
    fun getPerformance(idCourse: Int, idCompetitor: Int): PerformancesBdd

    @Query("SELECT * FROM PerformanceObstacleBdd WHERE performance_id = :id")
    fun getPerformanceObstacles(id: Int): Flow<List<PerformanceObstacleBdd>>



}