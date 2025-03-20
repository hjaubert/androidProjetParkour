package com.example.androidprojetparkour.api.models.performances

data class PerformancesItem(
    val access_token_id: Int,
    val competitor_id: Int,
    val course_id: Int,
    val created_at: String,
    val id: Int,
    val status: String,
    val total_time: Int,
    val updated_at: String
)