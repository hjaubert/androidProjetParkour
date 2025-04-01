package com.example.androidprojetparkour.bdd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Performance(

    @PrimaryKey(autoGenerate = true) var id: Int = 0,


)
