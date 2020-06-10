package com.uni.pis.ui.login

import com.uni.pis.Data.LoginData.LoginDataSource
import com.uni.pis.Data.LoginData.LoginRepository
import org.junit.Test

import org.junit.Assert.*

class LoginViewModelTest {

    @Test
    fun isUserNameValid() {
        var name="hamza basil yousef"
        var nameblank=""

        var dataSource=LoginDataSource()
        var loginRepository=LoginRepository(dataSource)
        var LoginViewModel=LoginViewModel(loginRepository)


        assertEquals(true,LoginViewModel.isUserNameValid(name))
        assertEquals(false,LoginViewModel.isUserNameValid(nameblank))

    }

    @Test
    fun isPasswordValid() {
        var pass10char="1234567890"
        var pass20char="12345678901234567890"
        var pass1char="1"
        var pass5char ="12345"
        var dataSource=LoginDataSource()
        var loginRepository=LoginRepository(dataSource)
        var LoginViewModel=LoginViewModel(loginRepository)


        assertEquals(10,LoginViewModel.isPasswordValid(pass10char))
        assertEquals(20,LoginViewModel.isPasswordValid(pass20char))
        assertNotEquals(1,LoginViewModel.isPasswordValid(pass1char))
        assertNotEquals(5,LoginViewModel.isPasswordValid(pass5char))

    }
}