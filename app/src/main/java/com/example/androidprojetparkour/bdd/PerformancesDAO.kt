package com.example.androidprojetparkour.bdd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PerformancesDAO {

    @Insert
    suspend fun insertPerformanceObstacle(performanceObstacle: PerformanceObstacleBdd)

    @Query("SELECT * FROM PerformancesBdd")
    fun getPerformances(): Flow<List<PerformanceObstacleBdd>>

    @Delete
    suspend fun deletePerformancesObstacle(performanceObstacle: PerformanceObstacleBdd)

    /*@Query("SELECT * FROM PerformanceObstacleBdd WHERE performance_id = :id")
    fun getPerformanceObstacles(id: Int): Flow<List<PerformanceObstacleBdd>>*/



}