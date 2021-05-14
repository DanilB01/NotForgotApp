package ru.tsu.lab3.model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.tsu.lab3.SharedPreference
import ru.tsu.lab3.network.ApiRepo
import ru.tsu.lab3.network.Network
import ru.tsu.lab3.network.UserLoginForm
import ru.tsu.lab3.network.UserRegistrationForm

class UserAccountModel(context: Context) {
    private val activity = context
    private val sharedPreference= SharedPreference(activity)
    private val apiRepo = ApiRepo(Network.retrofit)

    fun isOnline() = Network.isOnline(activity)

    suspend fun tryToLogin(emailInput: String, passwordInput: String): Boolean {
            try{
                Network.token = withContext(Dispatchers.IO) {
                    apiRepo.loginUser(UserLoginForm(emailInput, passwordInput)).apiToken
                }
            } catch(e: Exception) {
                return false
            }
        return if(Network.token != "") {
            sharedPreference.saveValueString("token", Network.token)
            true
        } else false
    }

    suspend fun tryToRegister(email: String, name: String, password: String): Boolean {
        val newUser = UserRegistrationForm(email, name, password)
        try {
            Network.token = withContext(Dispatchers.IO) {
                apiRepo.registerUser(newUser).apiToken
            }
        } catch (e:Exception) {
            return false
        }
        return if(Network.token != "") {
            sharedPreference.saveValueString("token", Network.token)
            true
        } else false
    }
}