package ru.tsu.lab3.presenter

import android.content.Context
import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tsu.lab3.interfaces.IEditNote
import ru.tsu.lab3.model.EditNoteModel
import ru.tsu.lab3.network.CategoryForm
import ru.tsu.lab3.network.Network.curTask
import ru.tsu.lab3.network.TaskForm

class EditNotePresenter(IView: IEditNote, context: Context) {
    private val model = EditNoteModel(context)
    private val view = IView

    fun setInitData() {
        GlobalScope.launch(Dispatchers.Main) {
            if(model.isOnline()) {
                model.initCategories()
                model.initPriorities()
            }
            else {
                model.initCategoriesFromDB()
                model.initPrioritiesFromDB()
            }
            view.setCategoryAdapter(
                model.getCategoryNames()
            )
            view.setPriorityAdapter(
                model.getPriorityNames()
            )
            if (curTask != null) {
                view.setAdapterCategoryByID(curTask!!.category.id - 1)
                view.setAdapterPriorityByID(curTask!!.priority.id - 2)
                view.setTaskTitle(curTask!!.title)
                view.setTaskDescription(curTask!!.description)
                //date???
            }
        }
    }

    fun saveTask() {
        val taskForm = TaskForm(
            view.getTaskTitleInput(),
            view.getTaskDescriptionInput(),
            null,
            null,
            model.getCategoryID(
                view.getTaskCategoryInput()
            ),
            model.getPriorityID(
                view.getTaskPriorityInput()
            )
        )
        if(curTask == null) {
            taskForm.done = 0
            if(model.isOnline())
                model.addTask(taskForm)
            else
                model.addTaskToDB(taskForm)
        } else {
            if(model.isOnline())
                model.editTask(taskForm)
            else
                model.editTaskToDB(taskForm)
        }

        view.goToTasksShow()
    }

    fun saveNewCategory(dialogView: View) {
        if(model.isOnline()) {
            model.saveNewCategory(
                CategoryForm(
                    view.getNewCategoryName(dialogView)
                )
            )
            GlobalScope.launch(Dispatchers.Main) {
                view.setCategoryAdapter(
                    withContext(Dispatchers.IO) {
                        model.getNewCategories()
                    }
                )
                view.setNewCategoryInAdapter()
            }
        } else {
            view.showNoConnectionMessage()
        }
    }

    fun backButtonPressed() {
        if(curTask != null) {
            view.goBack()
        } else {
            view.goToTasksShow()
        }
    }

}