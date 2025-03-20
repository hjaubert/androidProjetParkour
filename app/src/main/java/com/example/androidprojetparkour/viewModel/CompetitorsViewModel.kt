package com.example.androidprojetparkour.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitors.Competitors
import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem
import kotlinx.coroutines.launch

class CompetitorsViewModel: ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _competitorsResult = MutableLiveData<NetworkResponse<Competitors>>()
    val competitorsResult : LiveData<NetworkResponse<Competitors>> = _competitorsResult

    private val _competitorResult = MutableLiveData<NetworkResponse<CompetitorsItem>>()
    val competitorResult : LiveData<NetworkResponse<CompetitorsItem>> = _competitorResult

    fun getCompetitors(){
        viewModelScope.launch {
            _competitorsResult.value = NetworkResponse.Loading
            try {
                Log.d("Error", "0")
                val response = parkourApi.getCompetitors()
                Log.d("Error", "1")
                if(response.isSuccessful){
                    Log.d("Error", "2")
                    response.body()?.let {
                        _competitorsResult.value = NetworkResponse.Success(it)
                        Log.d("Error", "2")
                    }
                } else {
                    _competitorsResult.value = NetworkResponse.Error("Failed to load data")
                    Log.i("Response", response.body().toString())
                }
            } catch(e : Exception) {
                _competitorsResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

    fun getOneCompetitor(id: Int){
        viewModelScope.launch {
            _competitorResult.value  = NetworkResponse.Loading
            try {
                val response = parkourApi.getCompetitorsDetail(id)
                if(response.isSuccessful){
                    response.body()?.let {
                        _competitorResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitorResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch(e : Exception) {
                _competitorResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

}