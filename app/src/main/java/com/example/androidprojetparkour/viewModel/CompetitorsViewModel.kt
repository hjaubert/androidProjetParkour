package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.competitions.Competitions
import com.example.androidprojetparkour.api.models.competitors.Competitors

class CompetitorsViewModel {

    private val parkourApi = RetrofitInstance.parkourApi
    private val _competitorsResult = MutableLiveData<NetworkResponse<Competitors>>()

}