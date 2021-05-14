package ru.tsu.lab3.presenter

import ru.tsu.lab3.interfaces.INotesDetails
import ru.tsu.lab3.network.Network.curTask

class NotesDetailsPresenter(IView: INotesDetails) {
    private val view = IView

    fun setView() {
        view.setTitle(curTask!!.title)
        view.setDescription(curTask!!.description!!)
        view.setCategory(curTask!!.category.name)
        view.setDone(curTask!!.done)
        view.setPriority(curTask!!.priority.name)
    }
}