package com.example.androidprojetparkour.api.models.competitors

data class CompetitorsItem(
    val access_token_id: Int,
    val born_at: String,
    val created_at: String,
    val email: String,
    val first_name: String,
    val gender: String,
    val id: Int,
    val last_name: String,
    val phone: String,
    val updated_at: String
)