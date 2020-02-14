package com.uni.pis.data.model

import com.uni.pis.ui.login.ForgetPassword

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val displayName: String,
    val userId: String
)
