package com.ntsan.breakingbad.data.storage.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedCardIdEntity(
    @PrimaryKey
    val id: Int
)
