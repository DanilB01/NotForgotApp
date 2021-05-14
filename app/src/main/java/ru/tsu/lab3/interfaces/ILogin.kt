package ru.tsu.lab3.interfaces

interface ILogin {
    fun goToRegistration()
    fun goToTasksShow()
    fun getEmail(): String
    fun getPassword(): String
    fun showLoginError()
    fun showNoConnectionMessage()
}