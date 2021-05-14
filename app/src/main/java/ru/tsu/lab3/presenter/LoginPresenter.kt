package ru.tsu.lab3.presenter

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.tsu.lab3.interfaces.ILogin
import ru.tsu.lab3.model.UserAccountModel

class LoginPresenter(IView: ILogin, context: Context) {
    private val model = UserAccountModel(context)
    private val view = IView

    fun showNextFragment() {
        if(model.isOnline()) {
            GlobalScope.launch(Dispatchers.Main) {
                when (model.tryToLogin(view.getEmail(), view.getPassword())) {
                    true -> {
                        view.goToTasksShow()
                    }
                    false -> {
                        view.showLoginError()
                    }
                }
            }
        }
        else {
            view.showNoConnectionMessage()
        }
    }
}