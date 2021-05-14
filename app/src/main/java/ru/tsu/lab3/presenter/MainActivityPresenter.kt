package ru.tsu.lab3.presenter

import android.content.Context
import ru.tsu.lab3.interfaces.IMainActivity
import ru.tsu.lab3.model.MainActivityModel
import ru.tsu.lab3.network.Network.token

class MainActivityPresenter(IView: IMainActivity, context: Context) {
    private val model = MainActivityModel(context)
    private val view = IView

    fun chooseNextView() {
        model.getCurrentToken()
        if(token != "")
        {
            view.goToTasksShow()
        }
    }
}
