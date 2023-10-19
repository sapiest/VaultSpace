package com.sapiest.vaultspace.feature.auth.data.impl.local.model

import com.sapiest.vaultspace.feature.auth.data.api.models.UserModel
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id: String,
    val name: String?,
    val email: String?,
    val uriString: String?
)

fun UserModel.toUserData() = UserData(
    id = id,
    name = name,
    email = email,
    uriString = uriString
)

fun UserData.toUserModel() = UserModel(
    id = id,
    name = name,
    email = email,
    uriString = uriString
)