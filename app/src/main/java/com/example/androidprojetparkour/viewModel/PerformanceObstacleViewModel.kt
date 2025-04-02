package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.performances.Performances
import com.example.androidprojetparkour.api.models.performances.PerformancesItem
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstacles
import com.example.androidprojetparkour.api.models.performancesObstacles.PerformanceObstaclesItem
import kotlinx.coroutines.launch

class PerformanceObstacleViewModel : ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _performanceObstacles = MutableLiveData<NetworkResponse<PerformanceObstacles>>()
    val performanceObstacles: LiveData<NetworkResponse<PerformanceObstacles>> = _performanceObstacles

    private val _performanceObstacleDetails = MutableLiveData<NetworkResponse<PerformanceObstaclesItem>>()
    val performanceObstacleDetails: LiveData<NetworkResponse<PerformanceObstaclesItem>> = _performanceObstacleDetails

    private val _performanceDetails = MutableLiveData<NetworkResponse<PerformanceObstacles>>()
    val performancesDetails: LiveData<NetworkResponse<PerformanceObstacles>> = _performanceDetails

    private val _createPerformanceObstacleResult = MutableLiveData<NetworkResponse<PerformanceObstaclesItem>>()
    val createPerformanceObstacleResult: LiveData<NetworkResponse<PerformanceObstaclesItem>> = _createPerformanceObstacleResult

    private val _updatePerformanceObstacleResult = MutableLiveData<NetworkResponse<PerformanceObstaclesItem>>()
    val updatePerformanceObstacleResult: LiveData<NetworkResponse<PerformanceObstaclesItem>> = _updatePerformanceObstacleResult

    private val _deletePerformanceObstacleResult = MutableLiveData<NetworkResponse<Unit>>()
    val deletePerformanceObstacleResult: LiveData<NetworkResponse<Unit>> = _deletePerformanceObstacleResult

    val listTimeByObstacle = ArrayList<PerformanceObstaclesItem>()
    //val listTimeByObstacle: ArrayList<PerformanceObstaclesItem> = _listTimeByObstacle

    fun getPerformanceObstacles() {
        viewModelScope.launch {
            _performanceObstacles.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getPerformancesByObstacle()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _performanceObstacles.value = NetworkResponse.Success(it)
                    }
                } else {
                    _performanceObstacles.value = NetworkResponse.Error("Failed to load performance obstacles")
                }
            } catch (e: Exception) {
                _performanceObstacles.value = NetworkResponse.Error("Failed to load performance obstacles: ${e.message}")
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

    fun getPerformanceDetails(performanceId: Int) {
        viewModelScope.launch {
            _performanceDetails.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getDetailsPerformance(performanceId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _performanceDetails.value = NetworkResponse.Success(it)
                    }
                } else {
                    _performanceDetails.value = NetworkResponse.Error("Failed to load performance obstacle details")
                }
            } catch (e: Exception) {
                _performanceDetails.value = NetworkResponse.Error("Failed to load performance obstacle details: ${e.message}")
            }
        }
    }

    fun createPerformanceObstacle(performanceObstacle: PerformanceObstaclesItem) {
        viewModelScope.launch {
            _createPerformanceObstacleResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.storePerformanceObstacle(performanceObstacle)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createPerformanceObstacleResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _createPerformanceObstacleResult.value = NetworkResponse.Error("Failed to create performance obstacle")
                }
            } catch (e: Exception) {
                _createPerformanceObstacleResult.value = NetworkResponse.Error("Failed to create performance obstacle: ${e.message}")
            }
        }
    }

    fun updatePerformanceObstacle(performanceObstacleId: Int, performanceObstacle: PerformanceObstaclesItem) {
        viewModelScope.launch {
            _updatePerformanceObstacleResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.updatePerformanceObstacle(performanceObstacleId, performanceObstacle)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updatePerformanceObstacleResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _updatePerformanceObstacleResult.value = NetworkResponse.Error("Failed to update performance obstacle")
                }
            } catch (e: Exception) {
                _updatePerformanceObstacleResult.value = NetworkResponse.Error("Failed to update performance obstacle: ${e.message}")
            }
        }
    }

    fun addObstacleParkour(obstaclesItem: PerformanceObstaclesItem){
        listTimeByObstacle.add(obstaclesItem)
    }

}