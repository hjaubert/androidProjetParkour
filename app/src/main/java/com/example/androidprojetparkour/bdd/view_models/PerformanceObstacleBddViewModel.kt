package com.example.androidprojetparkour.bdd.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.bdd.BasePerformances
import com.example.androidprojetparkour.bdd.models.PerformanceObstacleBdd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PerformanceObstacleBddViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = BasePerformances.getInstance(application).perfDao()

    fun getPerformanceObstacles(id: Int):Flow<List<PerformanceObstacleBdd>> {
        return dao.getPerformanceObstacles(id)
    }

    fun ajouterPerformanceObstacle(obstacleId: Int, performanceId: Int, time: Long, hasFell: Int, toVerify: Int) {
        viewModelScope.launch {
            dao.insertPerformanceObstacle(PerformanceObstacleBdd(obstacle_id = obstacleId, performance_id = performanceId, time = time, has_fell = hasFell, to_verify = toVerify))
        }
    }
}