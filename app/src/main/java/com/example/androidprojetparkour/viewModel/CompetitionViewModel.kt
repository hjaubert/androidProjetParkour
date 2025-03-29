package com.example.androidprojetparkour.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.competitions.CompetitionsItem
import kotlinx.coroutines.launch

class CompetitionViewModel: ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _competitions = MutableLiveData<NetworkResponse<Competitions>>()
    val competitions : LiveData<NetworkResponse<Competitions>> = _competitions

    private val _oneCompetitor = MutableLiveData<NetworkResponse<CompetitionsItem>>()
    val oneCompetitor : LiveData<NetworkResponse<CompetitionsItem>> = _oneCompetitor

    fun getCompetitions(){
        viewModelScope.launch {
            _competitions.value = NetworkResponse.Loading
            try {
                Log.d("Error", "0")
                val response = parkourApi.getCompetitions()
                Log.d("Error", "1")
                if(response.isSuccessful){
                    Log.d("Error", "2")
                    response.body()?.let {
                        _competitions.value = NetworkResponse.Success(it)
                        Log.d("Error", "2")
                    }
                } else {
                    _competitions.value = NetworkResponse.Error("Failed to load data")
                    Log.i("Response", response.body().toString())
                }
            } catch(e : Exception) {
                _competitions.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

    fun getOneCompetition(id: Int){
        viewModelScope.launch {
            _oneCompetitor.value  = NetworkResponse.Loading
            try {
                val response = parkourApi.getOneCompetition(id)
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

}