package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.obstacles.Obstacles
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesCourse
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesItem
import kotlinx.coroutines.launch

class ObstacleViewModel : ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _obstacles = MutableLiveData<NetworkResponse<Obstacles>>()
    val obstacles: LiveData<NetworkResponse<Obstacles>> = _obstacles

    private val _obstacleDetails = MutableLiveData<NetworkResponse<ObstaclesItem>>()
    val obstacleDetails: LiveData<NetworkResponse<ObstaclesItem>> = _obstacleDetails

    private val _courseObstacles = MutableLiveData<NetworkResponse<ObstaclesCourse>>()
    val courseObstacles: LiveData<NetworkResponse<ObstaclesCourse>> = _courseObstacles

    private val _createObstacleResult = MutableLiveData<NetworkResponse<ObstaclesItem>>()
    val createObstacleResult: LiveData<NetworkResponse<ObstaclesItem>> = _createObstacleResult

    private val _updateObstacleResult = MutableLiveData<NetworkResponse<ObstaclesItem>>()
    val updateObstacleResult: LiveData<NetworkResponse<ObstaclesItem>> = _updateObstacleResult

    private val _deleteObstacleResult = MutableLiveData<NetworkResponse<Unit>>()
    val deleteObstacleResult: LiveData<NetworkResponse<Unit>> = _deleteObstacleResult

    fun getObstacles() {
        viewModelScope.launch {
            _obstacles.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getObstacles()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _obstacles.value = NetworkResponse.Success(it)
                    }
                } else {
                    _obstacles.value = NetworkResponse.Error("Failed to load obstacles")
                }
            } catch (e: Exception) {
                _obstacles.value = NetworkResponse.Error("Failed to load obstacles: ${e.message}")
            }
        }
    }

    fun getObstacleDetails(obstacleId: Int) {
        viewModelScope.launch {
            _obstacleDetails.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getDetailsObstacle(obstacleId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _obstacleDetails.value = NetworkResponse.Success(it)
                    }
                } else {
                    _obstacleDetails.value = NetworkResponse.Error("Failed to load obstacle details")
                }
            } catch (e: Exception) {
                _obstacleDetails.value = NetworkResponse.Error("Failed to load obstacle details: ${e.message}")
            }
        }
    }

    fun getCourseObstacles(courseId: Int) {
        viewModelScope.launch {
            _courseObstacles.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getObstacleInCourse(courseId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _courseObstacles.value = NetworkResponse.Success(it)
                    }
                } else {
                    _courseObstacles.value = NetworkResponse.Error("Failed to load course obstacles")
                }
            } catch (e: Exception) {
                _courseObstacles.value = NetworkResponse.Error("Failed to load course obstacles: ${e.message}")
            }
        }
    }

    fun createObstacle(obstacle: ObstaclesItem) {
        viewModelScope.launch {
            _createObstacleResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.storeObstacle(obstacle)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createObstacleResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _createObstacleResult.value = NetworkResponse.Error("Failed to create obstacle")
                }
            } catch (e: Exception) {
                _createObstacleResult.value = NetworkResponse.Error("Failed to create obstacle: ${e.message}")
            }
        }
    }

    fun updateObstacle(obstacleId: Int, obstacle: ObstaclesItem) {
        viewModelScope.launch {
            _updateObstacleResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.updateObstacle(obstacleId, obstacle)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updateObstacleResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _updateObstacleResult.value = NetworkResponse.Error("Failed to update obstacle")
                }
            } catch (e: Exception) {
                _updateObstacleResult.value = NetworkResponse.Error("Failed to update obstacle: ${e.message}")
            }
        }
    }

    fun deleteObstacle(obstacleId: Int) {
        viewModelScope.launch {
            _deleteObstacleResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.deleteObstacle(obstacleId)
                if (response.isSuccessful) {
                    _deleteObstacleResult.value = NetworkResponse.Success(Unit)
                } else {
                    _deleteObstacleResult.value = NetworkResponse.Error("Failed to delete obstacle")
                }
            } catch (e: Exception) {
                _deleteObstacleResult.value = NetworkResponse.Error("Failed to delete obstacle: ${e.message}")
            }
        }
    }
}