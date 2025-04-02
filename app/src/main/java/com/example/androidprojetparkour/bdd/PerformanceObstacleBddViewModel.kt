package com.example.androidprojetparkour.bdd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PerformanceObstacleBddViewModel(private val repository: Repository): ViewModel(){

    fun getPerformances() = repository.getPerformances().asLiveData(viewModelScope.coroutineContext)

    fun insertPerformanceObstacle(performanceObstacle: PerformanceObstacleBdd){
        viewModelScope.launch {
            repository.insertPerformanceObstacle(performanceObstacle)
        }
    }

    fun deletePerformanceObstacle(performanceObstacle: PerformanceObstacleBdd){
        viewModelScope.launch {
            repository.deletePerformancesObstacle(performanceObstacle)
        }
    }
}