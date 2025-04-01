package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import kotlinx.coroutines.launch

class CompetitorViewModel : ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _competitors = MutableLiveData<NetworkResponse<Competitors>>()
    val competitors: LiveData<NetworkResponse<Competitors>> = _competitors

    private val _competitorDetails = MutableLiveData<NetworkResponse<CompetitorsItem>>()
    val competitorDetails: LiveData<NetworkResponse<CompetitorsItem>> = _competitorDetails

    private val _createCompetitorResult = MutableLiveData<NetworkResponse<CompetitorsItem>>()
    val createCompetitorResult: LiveData<NetworkResponse<CompetitorsItem>> = _createCompetitorResult

    private val _updateCompetitorResult = MutableLiveData<NetworkResponse<CompetitorsItem>>()
    val updateCompetitorResult: LiveData<NetworkResponse<CompetitorsItem>> = _updateCompetitorResult

    private val _deleteCompetitorResult = MutableLiveData<NetworkResponse<Unit>>()
    val deleteCompetitorResult: LiveData<NetworkResponse<Unit>> = _deleteCompetitorResult

    fun getCompetitors() {
        viewModelScope.launch {
            _competitors.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getCompetitors()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _competitors.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitors.value = NetworkResponse.Error("Failed to load competitors")
                }
            } catch (e: Exception) {
                _competitors.value = NetworkResponse.Error("Failed to load competitors: ${e.message}")
            }
        }
    }

    fun getCompetitorDetails(competitorId: Int) {
        viewModelScope.launch {
            _competitorDetails.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getCompetitorsDetail(competitorId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _competitorDetails.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitorDetails.value = NetworkResponse.Error("Failed to load competitor details")
                }
            } catch (e: Exception) {
                _competitorDetails.value = NetworkResponse.Error("Failed to load competitor details: ${e.message}")
            }
        }
    }

    fun createCompetitor(competitor: CompetitorsItem) {
        viewModelScope.launch {
            _createCompetitorResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.storeCompetitor(competitor)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createCompetitorResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _createCompetitorResult.value = NetworkResponse.Error("Failed to create competitor")
                }
            } catch (e: Exception) {
                _createCompetitorResult.value = NetworkResponse.Error("Failed to create competitor: ${e.message}")
            }
        }
    }

    fun updateCompetitor(competitorId: Int, competitor: CompetitorsItem) {
        viewModelScope.launch {
            _updateCompetitorResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.updateCompetitor(competitorId, competitor)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updateCompetitorResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _updateCompetitorResult.value = NetworkResponse.Error("Failed to update competitor")
                }
            } catch (e: Exception) {
                _updateCompetitorResult.value = NetworkResponse.Error("Failed to update competitor: ${e.message}")
            }
        }
    }

    fun deleteCompetitor(competitorId: Int) {
        viewModelScope.launch {
            _deleteCompetitorResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.deleteCompetitor(competitorId)
                if (response.isSuccessful) {
                    _deleteCompetitorResult.value = NetworkResponse.Success(Unit)
                } else {
                    _deleteCompetitorResult.value = NetworkResponse.Error("Failed to delete competitor")
                }
            } catch (e: Exception) {
                _deleteCompetitorResult.value = NetworkResponse.Error("Failed to delete competitor: ${e.message}")
            }
        }
    }
}