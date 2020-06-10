package com.uni.pis.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.uni.pis.Data.LoginData.LoginRepository
import com.uni.pis.Data.LoginData.Result

import com.uni.pis.R

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    var mFirebaseAuth = FirebaseAuth.getInstance()
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String,save: Boolean ,sp:SharedPreferences) {
        // can be launched in a separate asynchronous job
        mFirebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener {
            if (!it.isSuccessful){
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
            else{

        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            if (save){

                var editor= sp.edit()

                editor.putString("name", username)
                editor.putString("password", password)

                editor.commit()

            }


        }
        }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()

    }

    // A placeholder password validation check
     fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
