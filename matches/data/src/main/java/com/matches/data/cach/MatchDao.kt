package com.matches.data.cach

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matches.data.cach.model.MatchEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MatchDao {

    @Insert
    suspend fun insertMatch(match: MatchEntity): Long

    @Query("DELETE FROM matches WHERE id = :primaryKey")
    suspend fun deleteMatch(primaryKey: Int): Int

    @Query("SELECT * FROM matches")
    fun getMatches(): Flow<List<MatchEntity>>

}