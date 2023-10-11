package com.sapiest.vaultspace.feature.currencyrates.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class NowResponse(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val seconds: Int,
    val milliSeconds: Int,
    val dateTime: String,
    val date: String,
    val time: String,
    val timeZone: String,
    val dayOfWeek: String,
    val dstActive: Boolean
)