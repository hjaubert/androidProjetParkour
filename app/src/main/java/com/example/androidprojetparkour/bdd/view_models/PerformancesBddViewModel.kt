package com.example.androidprojetparkour.bdd.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.bdd.BasePerformances
import com.example.androidprojetparkour.bdd.models.PerformancesBdd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PerformancesBddViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = BasePerformances.getInstance(application).perfDao()

    fun getPerformance(idCourse: Int, idCompetitor: Int): PerformancesBdd {
        return dao.getPerformance(idCourse, idCompetitor)
    }

    fun updatePerformance(performance: PerformancesBdd) {
        viewModelScope.launch {
            dao.updatePerformance(performance)
        }
    }

    fun ajouterPerformance(competitorId: Int, courseId: Int, status: String, totalTime: Long) {
        viewModelScope.launch {
            dao.insertPerformance(PerformancesBdd(competitor_id = competitorId, course_id = courseId, status = status, total_time = totalTime))
        }
    }
}