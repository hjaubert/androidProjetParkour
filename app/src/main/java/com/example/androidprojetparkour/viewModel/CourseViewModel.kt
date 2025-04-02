package com.example.androidprojetparkour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojetparkour.api.NetworkResponse
import com.example.androidprojetparkour.api.RetrofitInstance
import com.example.androidprojetparkour.api.models.courses.Courses
import com.example.androidprojetparkour.api.models.courses.CoursesItem
import com.example.androidprojetparkour.api.models.obstacles.ObstaclePost
import com.example.androidprojetparkour.api.models.obstacles.Obstacles
import com.example.androidprojetparkour.api.models.obstacles.ObstaclesItem
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

    private val _unusedObstacles = MutableLiveData<NetworkResponse<Obstacles>>()
    val unusedObstacles: LiveData<NetworkResponse<Obstacles>> = _unusedObstacles

    private val _createCourseResult = MutableLiveData<NetworkResponse<CoursesItem>>()
    val createCourseResult: LiveData<NetworkResponse<CoursesItem>> = _createCourseResult

    private val _updateCourseResult = MutableLiveData<NetworkResponse<CoursesItem>>()
    val updateCourseResult: LiveData<NetworkResponse<CoursesItem>> = _updateCourseResult

    private val _deleteCourseResult = MutableLiveData<NetworkResponse<Unit>>()
    val deleteCourseResult: LiveData<NetworkResponse<Unit>> = _deleteCourseResult

    private val _addObstacleResult = MutableLiveData<NetworkResponse<ObstaclesItem>>()
    val addObstacleResult: LiveData<NetworkResponse<ObstaclesItem>> = _addObstacleResult

    private val _removeObstacleResult = MutableLiveData<NetworkResponse<Unit>>()
    val removeObstacleResult: LiveData<NetworkResponse<Unit>> = _removeObstacleResult

    private val _updateObstaclePositionResult = MutableLiveData<NetworkResponse<ObstaclesItem>>()
    val updateObstaclePositionResult: LiveData<NetworkResponse<ObstaclesItem>> = _updateObstaclePositionResult

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

    fun getUnusedObstacles(courseId: Int) {
        viewModelScope.launch {
            _unusedObstacles.value = NetworkResponse.Loading
            try {
                val response = parkourApi.getUnusedObstacle(courseId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _unusedObstacles.value = NetworkResponse.Success(it)
                    }
                } else {
                    _unusedObstacles.value = NetworkResponse.Error("Failed to load competition courses")
                }
            } catch (e: Exception) {
                _unusedObstacles.value = NetworkResponse.Error("Failed to load competition courses: ${e.message}")
            }
        }
    }

    fun createCourse(course: CoursesItem) {
        viewModelScope.launch {
            _createCourseResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.storeCourse(course)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createCourseResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _createCourseResult.value = NetworkResponse.Error("Failed to create course")
                }
            } catch (e: Exception) {
                _createCourseResult.value = NetworkResponse.Error("Failed to create course: ${e.message}")
            }
        }
    }

    fun updateCourse(courseId: Int, course: CoursesItem) {
        viewModelScope.launch {
            _updateCourseResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.updateCourse(courseId, course)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updateCourseResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _updateCourseResult.value = NetworkResponse.Error("Failed to update course")
                }
            } catch (e: Exception) {
                _updateCourseResult.value = NetworkResponse.Error("Failed to update course: ${e.message}")
            }
        }
    }

    fun deleteCourse(courseId: Int) {
        viewModelScope.launch {
            _deleteCourseResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.deleteCourse(courseId)
                if (response.isSuccessful) {
                    _deleteCourseResult.value = NetworkResponse.Success(Unit)
                } else {
                    _deleteCourseResult.value = NetworkResponse.Error("Failed to delete course")
                }
            } catch (e: Exception) {
                _deleteCourseResult.value = NetworkResponse.Error("Failed to delete course: ${e.message}")
            }
        }
    }

    fun addObstacleToCourse(courseId: Int, obstaclePost: ObstaclePost) {
        viewModelScope.launch {
            _addObstacleResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.addObstacleToCourse(courseId, obstaclePost)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _addObstacleResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _addObstacleResult.value = NetworkResponse.Error("Failed to add obstacle to course")
                }
            } catch (e: Exception) {
                _addObstacleResult.value = NetworkResponse.Error("Failed to add obstacle to course: ${e.message}")
            }
        }
    }

    fun removeObstacleFromCourse(courseId: Int, obstacleId: Int) {
        viewModelScope.launch {
            _removeObstacleResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.removeObstacleFromCourse(courseId, obstacleId)
                if (response.isSuccessful) {
                    _removeObstacleResult.value = NetworkResponse.Success(Unit)
                } else {
                    _removeObstacleResult.value = NetworkResponse.Error("Failed to remove obstacle from course")
                }
            } catch (e: Exception) {
                _removeObstacleResult.value = NetworkResponse.Error("Failed to remove obstacle from course: ${e.message}")
            }
        }
    }

    fun updateObstaclePosition(courseId: Int, obstacleId: Int, position: Int) {
        viewModelScope.launch {
            _updateObstaclePositionResult.value = NetworkResponse.Loading
            try {
                val response = parkourApi.updateObstaclePosition(courseId, obstacleId, position)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updateObstaclePositionResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _updateObstaclePositionResult.value = NetworkResponse.Error("Failed to update obstacle position")
                }
                } catch (e: Exception) {
                _updateObstaclePositionResult.value = NetworkResponse.Error("Failed to update obstacle position: ${e.message}")
            }
        }
    }
}