package ru.tsu.lab3.model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tsu.lab3.SharedPreference
import ru.tsu.lab3.database.AddedTasks
import ru.tsu.lab3.database.AllTasks
import ru.tsu.lab3.database.AppDatabase
import ru.tsu.lab3.database.EditedTasks
import ru.tsu.lab3.network.*
import ru.tsu.lab3.network.Network.curTask

class EditNoteModel(context: Context) {
    private val activity = context
    private val apiRepo = ApiRepo(Network.retrofit)

    var prioritiesList = emptyArray<Priority>()
    var categoriesList = emptyArray<Category>()

    fun isOnline() = Network.isOnline(activity)

    suspend fun initCategories() {
        categoriesList = withContext(Dispatchers.IO) {
            apiRepo.getCategories(Network.token)
        }
    }

    suspend fun initPriorities() {
        prioritiesList = withContext(Dispatchers.IO) {
            apiRepo.getPriorities(Network.token)
        }
    }

    fun getCategoryNames(): Array<String> {
        var options = emptyArray<String>()
        for (cat in categoriesList) {
            options += cat.name
        }
        return options
    }

    fun getPriorityNames(): Array<String> {
        var prioritiesNames = emptyArray<String>()
        for (priority in prioritiesList) {
            prioritiesNames += priority.name
        }
        return prioritiesNames
    }

    suspend fun getNewCategories(): Array<String> {
        initCategories()
        return getCategoryNames()
    }

    fun getCategoryID(categoryInput: String): Int {
        for(category in categoriesList) {
            if(category.name == categoryInput)
                return category.id
        }
        return -1
    }

    fun getPriorityID(priorityInput: String): Int {
        for(priority in prioritiesList) {
            if(priority.name == priorityInput)
                return priority.id
        }
        return -1
    }

    fun addTask(taskForm: TaskForm) {
        if(Network.curTask == null) {
            GlobalScope.launch(Dispatchers.IO) {
                apiRepo.addTask(
                    Network.token,
                    taskForm
                )
            }
        }
    }

    fun editTask(taskForm: TaskForm) {
        if(Network.curTask != null) {
            GlobalScope.launch(Dispatchers.IO) {
                apiRepo.editTask(
                    Network.token,
                    Network.curTask!!.id,
                    taskForm
                )
            }
        }
    }

    fun saveNewCategory(categoryForm: CategoryForm) {
        GlobalScope.launch(Dispatchers.IO) {
            apiRepo.addCategory(
                Network.token,
                categoryForm
            )
        }
    }

    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //================================= FOR DATABASE ===============================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================

    suspend fun initCategoriesFromDB(){
        val dbCategoriesList = withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(activity).categoriesDao().getAll()
        }
        for(cat in dbCategoriesList) {
            categoriesList += Category(cat.id, cat.name)
        }
    }

    suspend fun initPrioritiesFromDB(){
        val dbPriorityList = withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(activity).priorityDao().getAll()
        }
        for(prior in dbPriorityList) {
            prioritiesList += Priority(prior.id, prior.name, prior.color)
        }
    }

    fun addToAllTasksForDB(taskForm: TaskForm) {
        GlobalScope.launch(Dispatchers.IO) {
            //AppDatabase.getDatabase(activity).allTasksDao().addTask(AllTasks(taskForm))
        }
    }

    fun addTaskToDB(taskForm: TaskForm) {
        GlobalScope.launch(Dispatchers.IO) {
            AppDatabase.getDatabase(activity).addedTasksDao().addTask(AddedTasks(taskForm.title, taskForm.description, taskForm.done, taskForm.deadline, taskForm.categoryID, taskForm.priorityID))
        }
        addToAllTasksForDB(taskForm)
    }

    fun editTaskToDB(taskForm: TaskForm) {
        GlobalScope.launch(Dispatchers.IO) {
            AppDatabase.getDatabase(activity).editedTasksDao().addTask(EditedTasks(curTask!!.id, taskForm.title, taskForm.description, taskForm.done, taskForm.deadline, taskForm.categoryID, taskForm.priorityID))
        }
        addToAllTasksForDB(taskForm)
    }
}