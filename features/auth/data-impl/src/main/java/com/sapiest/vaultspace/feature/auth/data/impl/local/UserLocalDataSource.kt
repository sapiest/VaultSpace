package com.sapiest.vaultspace.feature.auth.data.impl.local

import androidx.datastore.preferences.core.stringPreferencesKey
import com.sapiest.vaultspace.core.datastore.SafeDataStore
import com.sapiest.vaultspace.feature.auth.data.api.models.UserModel
import com.sapiest.vaultspace.feature.auth.data.impl.local.model.UserData
import com.sapiest.vaultspace.feature.auth.data.impl.local.model.toUserData
import com.sapiest.vaultspace.feature.auth.data.impl.local.model.toUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val dataStore: SafeDataStore,
    private val json: Json
) {
    suspend fun saveUser(userModel: UserModel) {
        dataStore.provide().updateData { preferences ->
            preferences.toMutablePreferences()[stringPreferencesKey(USER_MODEL)] =
                json.encodeToString(userModel.toUserData())
            preferences
        }
    }

    fun getUser(): Flow<Result<UserModel>> {
        return dataStore.provide().data.map { preferences ->
            preferences[stringPreferencesKey(USER_MODEL)]
        }.transform { stringModel ->
            val element = stringModel?.let { json.decodeFromString<UserData>(it) }
            emit(kotlin.runCatching {
                requireNotNull(element) { "UserModel not found" }.toUserModel()
            })
        }
    }

    fun signOut(): Flow<Result<Unit>> {
        return dataStore.provide().data.map {
            kotlin.runCatching {
                it.toMutablePreferences().clear()
            }
        }
    }

    private companion object {
        const val USER_MODEL = "userModel"
    }
}