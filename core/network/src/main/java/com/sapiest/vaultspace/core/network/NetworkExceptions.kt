package com.sapiest.vaultspace.core.network

import okio.IOException

class NetworkException(
    cause: IOException? = null,
    message: String = "Network Exception"
) : Exception(message, cause) {

    constructor(message: String) : this(cause = null, message = message)
}