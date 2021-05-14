package ru.tsu.lab3.interfaces

interface IRegistration {
    fun goToLogin()
    fun goToTasksShow()
    fun checkFieldsEmptiness(): Boolean
    fun getEmail(): String
    fun getName(): String
    fun getPassword(): String
    fun ifPasswordsSame(): Boolean
    fun showEmptyFieldsMessage()
    fun showDifferentPasswordsMessage()
    fun showRegisterError()
    fun showNoConnectionMessage()
}