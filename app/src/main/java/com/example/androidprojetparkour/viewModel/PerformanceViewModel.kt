package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.performances.CompetitorPerformance
import com.example.androidprojetparkour.api.models.performances.Performances
import com.example.androidprojetparkour.api.models.performances.PerformancesItem
import kotlinx.coroutines.launch

class PerformanceViewModel : ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _competitorPerformances = MutableLiveData<NetworkResponse<CompetitorPerformance>>()
    val competitorPerformances: LiveData<NetworkResponse<CompetitorPerformance>> = _competitorPerformances

    private val _performances = MutableLiveData<NetworkResponse<Performances>>()
    val performances: LiveData<NetworkResponse<Performances>> = _performances

    private val _performanceDetails = MutableLiveData<NetworkResponse<PerformancesItem>>()
    val performanceDetails: LiveData<NetworkResponse<PerformancesItem>> = _performanceDetails

    private val _coursePerformances = MutableLiveData<NetworkResponse<Performances>>()
    val coursePerformances: LiveData<NetworkResponse<Performances>> = _coursePerformances

    private val _createPerformanceResult = MutableLiveData<NetworkResponse<PerformancesItem>>()
    val createPerformanceResult: LiveData<NetworkResponse<PerformancesItem>> = _createPerformanceResult

    private val _updatePerformanceResult = MutableLiveData<NetworkResponse<PerformancesItem>>()
    val updatePerformanceResult: LiveData<NetworkResponse<PerformancesItem>> = _updatePerformanceResult

    private val _deletePerformanceResult = MutableLiveData<NetworkResponse<Unit>>()
    val deletePerformanceResult: LiveData<NetworkResponse<Unit>> = _deletePerformanceResult

    fun getCompetitorPerformances(competitorId: Int) {
        viewModelScope.launch {
            _competitorPerformances.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getPerformancesCompetitor(competitorId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _competitorPerformances.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitorPerformances.value = NetworkResponse.Error("Failed to load competitor performances")
                }
            } catch (e: Exception) {
                _competitorPerformances.value = NetworkResponse.Error("Failed to load competitor performances: ${e.message}")
            }
        }
    }

    fun getPerformances() {
        viewModelScope.launch {
            _performances.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getPerformances()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _performances.value = NetworkResponse.Success(it)
                    }
                } else {
                    _performances.value = NetworkResponse.Error("Failed to load performances")
                }
            } catch (e: Exception) {
                _performances.value = NetworkResponse.Error("Failed to load performances: ${e.message}")
            }
        }
    }

    fun getPerformanceDetails(performanceId: Int) {
        viewModelScope.launch {
            _performanceDetails.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getInformationPerformance(performanceId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _performanceDetails.value = NetworkResponse.Success(it)
                    }
                } else {
                    _performanceDetails.value = NetworkResponse.Error("Failed to load performance details")
                }
            } catch (e: Exception) {
                _performanceDetails.value = NetworkResponse.Error("Failed to load performance details: ${e.message}")
            }
        }
    }

    fun getCoursePerformances(courseId: Int) {
        viewModelScope.launch {
            _coursePerformances.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getPerformancesCourse(courseId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _coursePerformances.value = NetworkResponse.Success(it)
                    }
                } else {
                    _coursePerformances.value = NetworkResponse.Error("Failed to load course performances")
                }
            } catch (e: Exception) {
                _coursePerformances.value = NetworkResponse.Error("Failed to load course performances: ${e.message}")
            }
        }
    }

    fun createPerformance(performance: PerformancesItem) {
        viewModelScope.launch {
            _createPerformanceResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.storePerformance(performance)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createPerformanceResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _createPerformanceResult.value = NetworkResponse.Error("Failed to create performance")
                }
            } catch (e: Exception) {
                _createPerformanceResult.value = NetworkResponse.Error("Failed to create performance: ${e.message}")
            }
        }
    }

    fun updatePerformance(performanceId: Int, performance: PerformancesItem) {
        viewModelScope.launch {
            _updatePerformanceResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.updatePerformance(performanceId, performance)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updatePerformanceResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _updatePerformanceResult.value = NetworkResponse.Error("Failed to update performance")
                }
            } catch (e: Exception) {
                _updatePerformanceResult.value = NetworkResponse.Error("Failed to update performance: ${e.message}")
            }
        }
    }

    fun deletePerformance(performanceId: Int) {
        viewModelScope.launch {
            _deletePerformanceResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.deletePerformance(performanceId)
                if (response.isSuccessful) {
                    _deletePerformanceResult.value = NetworkResponse.Success(Unit)
                } else {
                    _deletePerformanceResult.value = NetworkResponse.Error("Failed to delete performance")
                }
            } catch (e: Exception) {
                _deletePerformanceResult.value = NetworkResponse.Error("Failed to delete performance: ${e.message}")
            }
        }
    }
}