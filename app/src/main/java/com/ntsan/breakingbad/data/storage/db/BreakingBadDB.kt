package com.ntsan.breakingbad.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.user.UserProfile
import com.ntsan.breakingbad.data.storage.db.entities.SavedCardIdEntity
import com.ntsan.breakingbad.data.storage.db.typeConvertors.StringListTypeConverter

@Database(
    entities = [BreakingBadCharacters::class, SavedCardIdEntity::class, UserProfile::class],
    version = 1
)
//@TypeConverters(
////    StringListTypeConverter::class,
//)
abstract class BreakingBadDB : RoomDatabase() {
    abstract fun getCardDAO(): CardDao
    abstract fun getSavedCardsDao(): SavedCardIdDao
    abstract fun getUserProfileDAO(): UserProfileDao
}