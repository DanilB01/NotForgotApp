package ru.tsu.lab3.interfaces

import android.view.View

interface IEditNote {
    fun setCategoryAdapter(options: Array<String>)
    fun setPriorityAdapter(prioritiesNames: Array<String>)
    fun setTaskTitle(text: String)
    fun setTaskDescription(text: String?)
    fun setNewCategoryInAdapter()
    fun setAdapterCategoryByID(id: Int) //       НЕ ЗАБУДЬ ПЕРЕДАВАТЬ В ЭТУ ФУНКЦИЮ ID-1 !!!!!!!!!!!!!!!!!!!!!
    fun setAdapterPriorityByID(id: Int) //       НЕ ЗАБУДЬ ПЕРЕДАВАТЬ В ЭТУ ФУНКЦИЮ ID-2 !!!!!!!!!!!!!!!!!!!!!

    fun getTaskTitleInput(): String
    fun getTaskDescriptionInput(): String
    fun getTaskDateInput(): String
    fun getTaskCategoryInput(): String
    fun getTaskPriorityInput(): String
    fun getNewCategoryName(dialogView: View): String

    fun ifFieldsEmpty(): Boolean

    fun showAddCategoryDialog(v: View)
    fun showNoConnectionMessage()

    fun goToTasksShow()
    fun goBack()
}
