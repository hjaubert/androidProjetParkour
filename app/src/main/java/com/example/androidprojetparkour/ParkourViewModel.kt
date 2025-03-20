package com.example.androidprojetparkour

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.models.Competitions
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import kotlinx.coroutines.launch

class ParkourViewModel: ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi
    private val _competitionsResult = MutableLiveData<NetworkResponse<Competitions>>()
    val parkourResult : LiveData<NetworkResponse<Competitions>> = _competitionsResult

    fun getData(){
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

}