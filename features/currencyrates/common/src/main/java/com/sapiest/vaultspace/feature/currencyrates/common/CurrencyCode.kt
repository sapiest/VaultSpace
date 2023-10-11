package com.sapiest.vaultspace.feature.currencyrates.common

/**
 * Для обозначения названия валют ISO-4217
 * https://www.iso.org/iso-4217-currency-codes.html
 * **/
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.TYPE
)
@Retention(AnnotationRetention.SOURCE)
annotation class CurrencyCode