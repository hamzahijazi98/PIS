package com.uni.pis.Data.LoginData

import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    // TODO: handle loggedInUser authentication


    fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            val User = LoggedInUser(username, password)
            return Result.Success(User)


        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }

    }

    fun logout() {
        // TODO: revoke authentication
    }
}

