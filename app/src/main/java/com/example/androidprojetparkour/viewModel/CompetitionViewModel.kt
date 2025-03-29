package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import kotlinx.coroutines.launch

class CompetitionViewModel : ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _competitions = MutableLiveData<NetworkResponse<Competitions>>()
    val competitions: LiveData<NetworkResponse<Competitions>> = _competitions

    private val _competitionDetails = MutableLiveData<NetworkResponse<CompetitionsItem>>()
    val competitionDetails: LiveData<NetworkResponse<CompetitionsItem>> = _competitionDetails

    private val _createCompetitionResult = MutableLiveData<NetworkResponse<CompetitionsItem>>()
    val createCompetitionResult: LiveData<NetworkResponse<CompetitionsItem>> = _createCompetitionResult

    private val _updateCompetitionResult = MutableLiveData<NetworkResponse<CompetitionsItem>>()
    val updateCompetitionResult: LiveData<NetworkResponse<CompetitionsItem>> = _updateCompetitionResult

    private val _deleteCompetitionResult = MutableLiveData<NetworkResponse<Unit>>()
    val deleteCompetitionResult: LiveData<NetworkResponse<Unit>> = _deleteCompetitionResult

    private val _addCompetitorResult = MutableLiveData<NetworkResponse<CompetitorsItem>>()
    val addCompetitorResult: LiveData<NetworkResponse<CompetitorsItem>> = _addCompetitorResult

    private val _removeCompetitorResult = MutableLiveData<NetworkResponse<Unit>>()
    val removeCompetitorResult: LiveData<NetworkResponse<Unit>> = _removeCompetitorResult

    fun getCompetitions() {
        viewModelScope.launch {
            _competitions.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getCompetitions()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _competitions.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitions.value = NetworkResponse.Error("Failed to load competitions")
                }
            } catch (e: Exception) {
                _competitions.value = NetworkResponse.Error("Failed to load competitions: ${e.message}")
            }
        }
    }

    fun getCompetitionDetails(competitionId: Int) {
        viewModelScope.launch {
            _competitionDetails.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getOneCompetition(competitionId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _competitionDetails.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitionDetails.value = NetworkResponse.Error("Failed to load competition details")
                }
            } catch (e: Exception) {
                _competitionDetails.value = NetworkResponse.Error("Failed to load competition details: ${e.message}")
            }
        }
    }

    fun createCompetition(competition: CompetitionsItem) {
        viewModelScope.launch {
            _createCompetitionResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.storeCompetition(competition)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createCompetitionResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _createCompetitionResult.value = NetworkResponse.Error("Failed to create competition")
                }
            } catch (e: Exception) {
                _createCompetitionResult.value = NetworkResponse.Error("Failed to create competition: ${e.message}")
            }
        }
    }

    fun updateCompetition(competitionId: Int, competition: CompetitionsItem) {
        viewModelScope.launch {
            _updateCompetitionResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.updateCompetition(competitionId, competition)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updateCompetitionResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _updateCompetitionResult.value = NetworkResponse.Error("Failed to update competition")
                }
            } catch (e: Exception) {
                _updateCompetitionResult.value = NetworkResponse.Error("Failed to update competition: ${e.message}")
            }
        }
    }

    fun deleteCompetition(competitionId: Int) {
        viewModelScope.launch {
            _deleteCompetitionResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.deleteCompetition(competitionId)
                if (response.isSuccessful) {
                    _deleteCompetitionResult.value = NetworkResponse.Success(Unit)
                } else {
                    _deleteCompetitionResult.value = NetworkResponse.Error("Failed to delete competition")
                }
            } catch (e: Exception) {
                _deleteCompetitionResult.value = NetworkResponse.Error("Failed to delete competition: ${e.message}")
            }
        }
    }

    fun addCompetitorToCompetition(competitionId: Int, competitorId: Int) {
        viewModelScope.launch {
            _addCompetitorResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.addCompetitorToCompetition(competitionId, competitorId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _addCompetitorResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _addCompetitorResult.value = NetworkResponse.Error("Failed to add competitor")
                }
            } catch (e: Exception) {
                _addCompetitorResult.value = NetworkResponse.Error("Failed to add competitor: ${e.message}")
            }
        }
    }

    fun removeCompetitorFromCompetition(competitionId: Int, competitorId: Int) {
        viewModelScope.launch {
            _removeCompetitorResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.removeCompetitorFromCompetition(competitionId, competitorId)
                if (response.isSuccessful) {
                    _removeCompetitorResult.value = NetworkResponse.Success(Unit)
                } else {
                    _removeCompetitorResult.value = NetworkResponse.Error("Failed to remove competitor")
                }
            } catch (e: Exception) {
                _removeCompetitorResult.value = NetworkResponse.Error("Failed to remove competitor: ${e.message}")
            }
        }
    }
}