package com.example.androidprojetparkour.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.performances.CompetitorPerformance
import com.example.androidprojetparkour.api.models.performances.Performances
import kotlinx.coroutines.launch

class PerformancesViewModel: ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _competitorPerformancesResult = MutableLiveData<NetworkResponse<CompetitorPerformance>>()
    val competitorPerformancesResult : LiveData<NetworkResponse<CompetitorPerformance>> = _competitorPerformancesResult

    fun getCompetitorPerformances(id: Int){
        viewModelScope.launch {
            _competitorPerformancesResult.value = NetworkResponse.Loading
            try {
                Log.d("Error", "0")
                val response = parkourApi.getPerformancesCompetitor(id)
                Log.d("Error", "1")
                if(response.isSuccessful){
                    Log.d("Error", "2")
                    response.body()?.let {
                        _competitorPerformancesResult.value = NetworkResponse.Success(it)
                        Log.d("Error", "2")
                        Log.d("Success", response.body().toString())
                    }
                } else {
                    _competitorPerformancesResult.value = NetworkResponse.Error("Failed to load data")
                    Log.i("Response", response.body().toString())
                }
            } catch(e : Exception) {
                _competitorPerformancesResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }

}