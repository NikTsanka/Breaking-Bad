package com.ntsan.breakingbad.data.storage.db

import androidx.room.*
import com.ntsan.breakingbad.data.storage.db.entities.SavedCardIdEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SavedCardIdDao {

    @Query("SELECT * FROM SavedCardIdEntity")
    fun getSavedCards(): List<SavedCardIdEntity>

    @Query("SELECT * FROM SavedCardIdEntity")
    fun getSavedCardFlow(): Flow<List<SavedCardIdEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(ids: List<SavedCardIdEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(id: SavedCardIdEntity)

    @Delete
    fun delete(vararg ids: SavedCardIdEntity)

    @Query("DELETE FROM SavedCardIdEntity")
    fun deleteAll()

}