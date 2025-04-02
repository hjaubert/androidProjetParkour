package com.example.androidprojetparkour.bdd.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "performance_obstacles")
data class PerformanceObstacleBdd(

    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var obstacle_id: Int = 0,
    var performance_id: Int = 0,
    var time: Long = 0,
    var has_fell: Int = 0,
    var to_verify: Int = 0
)
