package com.uni.pis.data

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.uni.pis.data.model.LoggedInUser
import java.io.IOError
import java.io.IOException
import com.uni.pis.ui.login.LoginActivity as LoginActivity1

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    // TODO: handle loggedInUser authentication


    fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            val User = LoggedInUser(username,password)
            return  Result.Success(User)


        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }

    }

    fun logout() {
        // TODO: revoke authentication
    }
}

