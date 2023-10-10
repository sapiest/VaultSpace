package com.sapiest.vaultspace.core.network

import okio.IOException
import retrofit2.Call

class ApiCall<T>(private val call: Call<T>) {
    fun execute(): Result<T> {
        return try {
            val response = call.execute()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(
                    NetworkException(
                        "Network error: ${response.errorBody()?.toString() ?: "Unknown error"}"
                    )
                )
            }
        } catch (e: IOException) {
            Result.failure(NetworkException(e))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}