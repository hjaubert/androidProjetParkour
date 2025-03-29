package com.example.androidprojetparkour.api.models.courses

data class CoursesItem(
    val competition_id: Int,
    val created_at: String,
    val id: Int,
    val is_over: Int,
    val max_duration: Int,
    val name: String,
    val position: Int,
    val updated_at: String
)