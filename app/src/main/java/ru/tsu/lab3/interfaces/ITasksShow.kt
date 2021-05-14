package ru.tsu.lab3.interfaces

import ru.tsu.lab3.Dates

interface ITasksShow {
    fun goToEditNote()
    fun goToLogin()

    fun removePlaceholder()
    fun setPlaceholder()

    fun showTaskDeletedMessage()
    fun showLogoutMessage()
    fun showNoConnectionMessage()

    fun setRecyclerView(resultArray: MutableList<Dates>)
    fun changeRecyclerAdapter()
}