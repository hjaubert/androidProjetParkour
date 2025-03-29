package com.example.androidprojetparkour.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import kotlinx.coroutines.launch

class CompetitorViewModel: ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _competitors = MutableLiveData<NetworkResponse<Competitors>>()
    val competitors : LiveData<NetworkResponse<Competitors>> = _competitors

    private val _oneCompetitor = MutableLiveData<NetworkResponse<CompetitorsItem>>()
    val oneCompetitor : LiveData<NetworkResponse<CompetitorsItem>> = _oneCompetitor

    private val _registeredCompetitorsInCompetition = MutableLiveData<NetworkResponse<Competitors>>()
    val registeredCompetitorsInCompetition : LiveData<NetworkResponse<Competitors>> = _registeredCompetitorsInCompetition

    fun getCompetitors(){
        viewModelScope.launch {
            _competitors.value = NetworkResponse.Loading
            try {
                Log.d("Error", "0")
                val response = parkourApi.getCompetitors()
                Log.d("Error", "1")
                if(response.isSuccessful){
                    Log.d("Error", "2")
                    response.body()?.let {
                        _competitors.value = NetworkResponse.Success(it)
                        Log.d("Error", "2")
                    }
                } else {
                    _competitors.value = NetworkResponse.Error("Failed to load data")
                    Log.i("Response", response.body().toString())
                }
            } catch(e : Exception) {
                _competitors.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

    fun getOneCompetitor(id: Int){
        viewModelScope.launch {
            _oneCompetitor.value  = NetworkResponse.Loading
            try {
                val response = parkourApi.getCompetitorsDetail(id)
                if(response.isSuccessful){
                    response.body()?.let {
                        _oneCompetitor.value = NetworkResponse.Success(it)
                    }
                } else {
                    _oneCompetitor.value = NetworkResponse.Error("Failed to load data")
                }
            } catch(e : Exception) {
                _oneCompetitor.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

    fun getRegisteredCompetitorsInCompetition(competitionId: Int){
        viewModelScope.launch {
            _registeredCompetitorsInCompetition.value  = NetworkResponse.Loading
            try {
                val response = parkourApi.getRegisteredCompetitorsInCompetition(competitionId)
                if(response.isSuccessful){
                    response.body()?.let {
                        _registeredCompetitorsInCompetition.value = NetworkResponse.Success(it)
                    }
                } else {
                    _registeredCompetitorsInCompetition.value = NetworkResponse.Error("Failed to load data")
                }
            } catch(e : Exception) {
                _registeredCompetitorsInCompetition.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

}