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

class CompetitionsViewModel: ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _competitionsResult = MutableLiveData<NetworkResponse<Competitions>>()
    val competitionsResult : LiveData<NetworkResponse<Competitions>> = _competitionsResult

    private val _competitionResult = MutableLiveData<NetworkResponse<CompetitionsItem>>()
    val competitionResult : LiveData<NetworkResponse<CompetitionsItem>> = _competitionResult

    fun getCompetitions(){
        viewModelScope.launch {
            _competitionsResult.value = NetworkResponse.Loading
            try {
                Log.d("Error", "0")
                val response = parkourApi.getCompetitions()
                Log.d("Error", "1")
                if(response.isSuccessful){
                    Log.d("Error", "2")
                    response.body()?.let {
                        _competitionsResult.value = NetworkResponse.Success(it)
                        Log.d("Error", "2")
                    }
                } else {
                    _competitionsResult.value = NetworkResponse.Error("Failed to load data")
                    Log.i("Response", response.body().toString())
                }
            } catch(e : Exception) {
                _competitionsResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

    fun getOneCompetition(id: Int){
        viewModelScope.launch {
            _competitionResult.value  = NetworkResponse.Loading
            try {
                val response = parkourApi.getOneCompetition(id)
                if(response.isSuccessful){
                    response.body()?.let {
                        _competitionResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitionResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch(e : Exception) {
                _competitionResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

}