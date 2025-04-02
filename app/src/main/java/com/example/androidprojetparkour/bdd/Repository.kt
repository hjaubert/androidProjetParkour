package com.example.androidprojetparkour.bdd

class Repository(private val db: BasePerformances) {

    suspend fun insertPerformanceObstacle(performanceObstacle: PerformanceObstacleBdd){
        db.perfDao().insertPerformanceObstacle(performanceObstacle)
    }

    suspend fun deletePerformancesObstacle(performanceObstacle: PerformanceObstacleBdd){
        db.perfDao().deletePerformancesObstacle(performanceObstacle)
    }

    fun getPerformances() = db.perfDao().getPerformances()

}