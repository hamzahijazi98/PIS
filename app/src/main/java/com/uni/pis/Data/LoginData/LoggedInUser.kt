package com.uni.pis.Data.LoginData

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val displayName: String,
    val userId: String
)
