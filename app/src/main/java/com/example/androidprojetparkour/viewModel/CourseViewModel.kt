package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.courses.Courses
import com.example.androidprojetparkour.api.models.courses.CoursesItem
import kotlinx.coroutines.launch

class CourseViewModel : ViewModel() {

    private val parkourApi = RetrofitInstance.parkourApi

    private val _courses = MutableLiveData<NetworkResponse<Courses>>()
    val courses: LiveData<NetworkResponse<Courses>> = _courses

    private val _courseDetails = MutableLiveData<NetworkResponse<CoursesItem>>()
    val courseDetails: LiveData<NetworkResponse<CoursesItem>> = _courseDetails

    private val _competitorCourses = MutableLiveData<NetworkResponse<Courses>>()
    val competitorCourses: LiveData<NetworkResponse<Courses>> = _competitorCourses

    private val _competitionCourses = MutableLiveData<NetworkResponse<Courses>>()
    val competitionCourses: LiveData<NetworkResponse<Courses>> = _competitionCourses

    fun getCourses() {
        viewModelScope.launch {
            _courses.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getCourses()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _courses.value = NetworkResponse.Success(it)
                    }
                } else {
                    _courses.value = NetworkResponse.Error("Failed to load courses")
                }
            } catch (e: Exception) {
                _courses.value = NetworkResponse.Error("Failed to load courses: ${e.message}")
            }
        }
    }

    fun getCourseDetails(courseId: Int) {
        viewModelScope.launch {
            _courseDetails.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getDetailsCourse(courseId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _courseDetails.value = NetworkResponse.Success(it)
                    }
                } else {
                    _courseDetails.value = NetworkResponse.Error("Failed to load course details")
                }
            } catch (e: Exception) {
                _courseDetails.value = NetworkResponse.Error("Failed to load course details: ${e.message}")
            }
        }
    }

    fun getCompetitorCourses(competitorId: Int) {
        viewModelScope.launch {
            _competitorCourses.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getCoursesCompetitorIsIn(competitorId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _competitorCourses.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitorCourses.value = NetworkResponse.Error("Failed to load competitor courses")
                }
            } catch (e: Exception) {
                _competitorCourses.value = NetworkResponse.Error("Failed to load competitor courses: ${e.message}")
            }
        }
    }

    fun getCompetitionCourses(competitionId: Int) {
        viewModelScope.launch {
            _competitionCourses.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getCompetitionCourses(competitionId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _competitionCourses.value = NetworkResponse.Success(it)
                    }
                } else {
                    _competitionCourses.value = NetworkResponse.Error("Failed to load competition courses")
                }
            } catch (e: Exception) {
                _competitionCourses.value = NetworkResponse.Error("Failed to load competition courses: ${e.message}")
            }
        }
    }
}