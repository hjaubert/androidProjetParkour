package com.example.androidprojetparkour.bdd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1
)
abstract class BasePerformances : RoomDatabase() {

    abstract fun perfDao() : PerformancesDAO

    companion object {
        fun getInstance(context : Context): BasePerformances = lazy {
            Room.databaseBuilder(
                context,
                BasePerformances::class.java, "modules.sqlite"
            ).build()
        }.value
    }

}