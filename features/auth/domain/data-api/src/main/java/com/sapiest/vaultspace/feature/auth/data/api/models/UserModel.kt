package com.sapiest.vaultspace.feature.auth.data.api.models


data class UserModel(
    val id: String,
    val name: String? = null,
    val email: String? = null,
    val uriString: String? = null,
)