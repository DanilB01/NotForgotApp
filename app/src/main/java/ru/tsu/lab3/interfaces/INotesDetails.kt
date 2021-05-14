package ru.tsu.lab3.interfaces

interface INotesDetails {
    fun goToEditNote()
    fun setTitle(text: String)
    fun setDescription(text: String)
    fun setCategory(text: String)
    fun setDone(isDone: Int)
    fun setPriority(priorityText: String)
}