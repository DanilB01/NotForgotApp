package ru.tsu.lab3.presenter

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.tsu.lab3.interfaces.IRegistration
import ru.tsu.lab3.model.UserAccountModel

class RegistrationPresenter(IView: IRegistration, context: Context) {
    private val model = UserAccountModel(context)
    private val view = IView

    fun showNextFragment() {
        if(model.isOnline()) {
            when {
                view.checkFieldsEmptiness() -> {
                    view.showEmptyFieldsMessage()
                }
                view.ifPasswordsSame() -> {
                    view.showDifferentPasswordsMessage()
                }
                else -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        when (model.tryToRegister(
                            view.getEmail(),
                            view.getName(),
                            view.getPassword()
                        )) {
                            true -> {
                                view.goToTasksShow()
                            }
                            false -> {
                                view.showRegisterError()
                            }
                        }
                    }
                }
            }
        } else {
            view.showNoConnectionMessage()
        }
    }
}