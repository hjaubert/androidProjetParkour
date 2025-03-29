package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstacles
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstaclesItem
import kotlinx.coroutines.launch

class PerformanceObstacleViewModel: ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _performancesObstacle = MutableLiveData<NetworkResponse<PerformanceObstacles>>()
    val performancesObstacle: LiveData<NetworkResponse<PerformanceObstacles>> = _performancesObstacle

    private val _performanceObstacleDetails = MutableLiveData<NetworkResponse<PerformanceObstaclesItem>>()
    val performanceObstacleDetails: LiveData<NetworkResponse<PerformanceObstaclesItem>> = _performanceObstacleDetails

    private val _performanceObstaclesByPerformance = MutableLiveData<NetworkResponse<PerformanceObstacles>>()
    val performanceObstaclesByPerformance: LiveData<NetworkResponse<PerformanceObstacles>> = _performanceObstaclesByPerformance

    fun getPerformancesObstacle() {
        viewModelScope.launch {
            _performancesObstacle.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getPerformancesByObstacle()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _performancesObstacle.value = NetworkResponse.Success(it)
                    }
                } else {
                    _performancesObstacle.value = NetworkResponse.Error("Failed to load performances obstacles")
                }
            } catch (e: Exception) {
                _performancesObstacle.value = NetworkResponse.Error("Failed to load performances obstacles: ${e.message}")
            }
        }
    }

    fun getPerformanceObstacleDetails(performanceObstacleId: Int) {
        viewModelScope.launch {
            _performanceObstacleDetails.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getDetailsPerformanceObstacle(performanceObstacleId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _performanceObstacleDetails.value = NetworkResponse.Success(it)
                    }
                } else {
                    _performanceObstacleDetails.value = NetworkResponse.Error("Failed to load performance obstacle details")
                }
            } catch (e: Exception) {
                _performanceObstacleDetails.value = NetworkResponse.Error("Failed to load performance obstacle details: ${e.message}")
            }
        }
    }

    fun getDetailsPerformance(performanceId: Int) {
        viewModelScope.launch {
            _performanceObstaclesByPerformance.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getDetailsPerformance(performanceId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _performanceObstaclesByPerformance.value = NetworkResponse.Success(it)
                    }
                } else {
                    _performanceObstaclesByPerformance.value = NetworkResponse.Error("Failed to load performance obstacles by performance")
                }
            } catch (e: Exception) {
                _performanceObstaclesByPerformance.value = NetworkResponse.Error("Failed to load performance obstacles by performance: ${e.message}")
            }
        }
    }

}