package com.example.androidprojetparkour.bdd.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PerformancesBdd(

    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var competitor_id: Int = 0,
    var course_id: Int = 0,
    var status: String = "",
    var total_time: Long = 0
)
