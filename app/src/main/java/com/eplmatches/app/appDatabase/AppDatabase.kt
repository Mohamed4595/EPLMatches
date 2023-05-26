package com.eplmatches.app.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matches.data.cach.MatchDao
import com.matches.data.cach.model.MatchEntity


@Database(entities = [MatchEntity::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun matchDao(): MatchDao

    companion object{
        val DATABASE_NAME: String = "favourite_matches_db"
    }

}