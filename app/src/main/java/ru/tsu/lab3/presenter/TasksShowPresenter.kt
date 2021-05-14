package ru.tsu.lab3.presenter

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tasks_show.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tsu.lab3.Dates
import ru.tsu.lab3.RecyclerViewAdapter
import ru.tsu.lab3.SharedPreference
import ru.tsu.lab3.interfaces.ITasksShow
import ru.tsu.lab3.model.TasksShowModel
import ru.tsu.lab3.network.Network
import ru.tsu.lab3.network.Task

class TasksShowPresenter(IView: ITasksShow, context: Context) {
    private val model = TasksShowModel(context)
    private val view = IView


    fun initNotesList() {

            GlobalScope.launch(Dispatchers.Main) {
                if(model.isOnline()) {
                    model.initTasks()
                    model.initCategories()

                } else {
                    model.initTasksFromDB()
                    model.initCategoriesFromDB()
                }
                if (model.ifTasksExist()) {
                    view.removePlaceholder()
                    //model.initCategories()
                    view.setRecyclerView(
                        model.getListForRecyclerView()
                    )
                }
            }
        if(model.isOnline()) {
            model.postNewData()
        }
    }

    fun addNotePressed() {
        Network.curTask = null
        view.goToEditNote()
    }

    fun logoutPressed() {
        model.logout()
        view.showLogoutMessage()
        view.goToLogin()
    }

    fun taskSwiped(position: Int) {
        if(model.ifTaskSelected(position)) {
            model.deleteTask(position)
            view.showTaskDeletedMessage()
            view.changeRecyclerAdapter()
            if(!model.ifTasksExist()){
                view.setPlaceholder()
            }
        }
    }
}