package com.ntsan.breakingbad.data.repository

import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.data.storage.db.entities.SavedCardIdEntity
import com.ntsan.breakingbad.data.models.breakingbad.BreakingBadCharacters
import com.ntsan.breakingbad.data.models.user.UserRegistration
import com.ntsan.breakingbad.data.network.NetworkClient
import kotlinx.coroutines.flow.map

object Repository {

    suspend fun getLocalCardById(id: Int): BreakingBadCharacters? {
        return DataStore.db.getCardDAO().getById(id)
    }
/*
    suspend fun getRemoteCardById(id: Int): BreakingBadCharacters {
        return NetworkClient.breakingBadService.getCardById(id).to
            .also {
                DataStore.db.getCardDAO().insert(it)
            }
    }*/

    suspend fun getRemoteCardsCardsAndStore(
        limit: Int,
        offset: Int
    ): List<BreakingBadCharacters> {
        val cards = NetworkClient.breakingBadService.getCharacter(
            limit = limit,
            offset = offset
        )
        DataStore.db.getCardDAO().insert(cards)
        return cards
    }

    fun getLocalSavedCardsFlow() =
        DataStore.db.getSavedCardsDao().getSavedCardFlow()
            .map { list -> list.map { it.id } }

    suspend fun clearSavedCards() =
        DataStore.db.getSavedCardsDao().deleteAll()


    suspend fun updateRemoteSavedCards(): List<Int> {
        val ids = NetworkClient.userService.getUserCards().map { it }
        DataStore.db.getSavedCardsDao().insert(ids = ids.map { SavedCardIdEntity(it) })
        DataStore.lastTimeSavedCardsFetched = System.currentTimeMillis()
        return ids
    }

    suspend fun loginAndSetToken(username: String, password: String) {
        NetworkClient.userService.login(
            username = username,
            password = password
        ).apply {
            DataStore.authToken = accessToken
        }
    }

    suspend fun registerAndLogin(
        name: String,
        userName: String,
        password: String
    ) {
        NetworkClient.userService.createUser(
            userRegistration = UserRegistration(
                name = name,
                userName = userName,
                password = password
            )
        )
        NetworkClient.userService.login(
            username = userName,
            password = password
        ).accessToken.apply {
            DataStore.authToken = this
        }
    }

    suspend fun saveCard(card: BreakingBadCharacters) {
        NetworkClient.userService.saveUserCards(card.charId)
        DataStore.db.getSavedCardsDao().insert(SavedCardIdEntity(card.charId))
    }

    suspend fun deleteCard(card: BreakingBadCharacters) {
        NetworkClient.userService.deleteUserCard(card.charId)
        DataStore.db.getSavedCardsDao().delete(SavedCardIdEntity(card.charId))
    }

    suspend fun checkSavedIdsValidity(): Boolean =
        System.currentTimeMillis() - DataStore.lastTimeSavedCardsFetched < 60 * 60 * 1000

    suspend fun invalidateSavedIds() {
        DataStore.lastTimeSavedCardsFetched = 0
    }

    suspend fun getRemoteAndSaveProfile() {
        DataStore.db.getUserProfileDAO().insert(
            NetworkClient.userService.getUser()
        )
    }

    suspend fun clearProfile() {
        DataStore.db.getUserProfileDAO().delete()
    }
}