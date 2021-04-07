package com.ntsan.breakingbad.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters

@Dao
interface CardDao {

    @Query("SELECT * FROM BreakingBadCharacters")
    suspend fun getAll(): List<BreakingBadCharacters>

    @Query("SELECT * FROM BreakingBadCharacters WHERE charId =:id")
    suspend fun getById(id: Int): BreakingBadCharacters?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(BrBadCharacters: BreakingBadCharacters)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(BrBadCharacters: List<BreakingBadCharacters>)
}