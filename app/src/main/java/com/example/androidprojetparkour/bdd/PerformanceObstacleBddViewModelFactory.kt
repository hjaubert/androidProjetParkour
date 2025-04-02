package com.example.androidprojetparkour.bdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PerformanceObstacleBddViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerformanceObstacleBddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerformanceObstacleBddViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}