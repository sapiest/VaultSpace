package com.sapiest.vaultspace.core.database.providers

fun interface DaoProvider<T> {
    suspend operator fun invoke(): T
}