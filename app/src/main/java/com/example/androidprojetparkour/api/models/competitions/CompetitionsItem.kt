package com.example.androidprojetparkour.api.models.competitions

data class CompetitionsItem(
    val age_max: Int,
    val age_min: Int,
    val created_at: String,
    val gender: String,
    val has_retry: Int,
    val id: Int,
    val name: String,
    val status: String,
    val updated_at: String
)