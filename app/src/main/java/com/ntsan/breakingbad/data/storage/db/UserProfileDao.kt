package com.ntsan.breakingbad.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ntsan.breakingbad.data.models.user.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao

interface UserProfileDao {

    @Query("SELECT * FROM userprofile WHERE id = 1 ")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert
    fun insert(userProfile: UserProfile)

    @Query("DELETE FROM userprofile")
    fun delete()
}