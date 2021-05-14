package ru.tsu.lab3.model

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.tsu.lab3.Dates
import ru.tsu.lab3.SharedPreference
import ru.tsu.lab3.database.AllTasks
import ru.tsu.lab3.database.AppDatabase
import ru.tsu.lab3.database.CategoryForDB
import ru.tsu.lab3.database.PriorityForDB
import ru.tsu.lab3.network.*


class TasksShowModel(context: Context) {
    private val activity = context
    private val sharedPreference = SharedPreference(activity)
    private val apiRepo = ApiRepo(Network.retrofit)

    var resultArray = mutableListOf<Dates>()
    var taskList = emptyArray<Task>()
    var categoryList = emptyArray<Category>()

    fun isOnline() = Network.isOnline(activity)

    fun logout() {
        sharedPreference.removeValue("token")
    }

    suspend fun setCategoryInDB() {
        GlobalScope.launch(Dispatchers.IO) {
        for (cat in categoryList) {
            AppDatabase.getDatabase(activity).categoriesDao().add(
                CategoryForDB(
                    cat.name,
                    cat.id
                )
            )
        }
        }
    }

    suspend fun setPriorityInDB() {
        GlobalScope.launch(Dispatchers.IO) {
        val priorList = apiRepo.getPriorities(Network.token)
        for (pr in priorList) {
            AppDatabase.getDatabase(activity).priorityDao().add(
                PriorityForDB(
                    pr.name,
                    pr.color,
                    pr.id
                )
            )
        }
        }
    }

    suspend fun initTasks() {
        taskList = withContext(Dispatchers.IO) {
            apiRepo.getTasks(Network.token)
        }
        setTasksInDB()
    }

    suspend fun initCategories() {
        categoryList = withContext(Dispatchers.IO) {
            apiRepo.getCategories(Network.token)
        }
        setCategoryInDB()
        setPriorityInDB()
    }

    fun ifTasksExist(): Boolean {
        return taskList.isNotEmpty()
    }

    fun getListForRecyclerView(): MutableList<Dates> {
        resultArray.clear()
        val tempTaskListByCategory = mutableListOf<Task>()
        for (category in categoryList) {
            for (task in taskList) {
                if (task.category.name == category.name) {
                    tempTaskListByCategory.add(task)
                }
            }
            if (tempTaskListByCategory.isNotEmpty()) {
                resultArray.add(Dates(0, null, category))
                for (task in tempTaskListByCategory) {
                    resultArray.add(Dates(1, task, null))
                }
                tempTaskListByCategory.clear()
            }
        }
        return resultArray
    }

    fun ifTaskSelected(position: Int): Boolean {
        return resultArray[position].type == 1
    }

    fun deleteTask(position: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val curCategoryName = resultArray[position].task!!.category.name
            launch(Dispatchers.IO) {
                apiRepo.deleteTask(Network.token, resultArray[position].task!!.id)
            }
            resultArray.removeAt(position)
            if (resultArray.size == 1) {
                resultArray.clear()
            } else {
                for (i in 0 until resultArray.size) {
                    if (resultArray[i].type == 0 &&
                        resultArray[i].category!!.name == curCategoryName
                    ) {
                        if ((i + 1 < resultArray.size && resultArray[i + 1].type == 0) ||
                            i + 1 == resultArray.size
                        ) {
                            resultArray.removeAt(i)
                            break
                        }
                    }
                }
            }
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

    suspend fun initTasksFromDB() {
        val dbTasks = withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(activity).allTasksDao().getAll()
        }
        for (task in dbTasks) {
            taskList += Task(
                task.id,
                task.title,
                task.description,
                task.done,
                task.deadline,
                Category(0, task.categoryName),
                Priority(0, task.priorityName, task.priorityColor),
                task.created
            )
        }
    }

    suspend fun initCategoriesFromDB() {
        val dbCategories = withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(activity).categoriesDao().getAll()
        }
        for (cat in dbCategories) {
            categoryList += Category(cat.id, cat.name)
        }
    }

    suspend fun setTasksInDB() {
        GlobalScope.launch(Dispatchers.IO) {
            for (task in taskList) {
                AppDatabase.getDatabase(activity).allTasksDao().addTask(
                    AllTasks(
                        task.title,
                        task.description,
                        task.done,
                        task.deadline,
                        task.category.name,
                        task.priority.name,
                        task.priority.color,
                        task.created
                    )
                )
            }
        }

    }

        fun postNewData() {
            GlobalScope.launch(Dispatchers.IO) {
                val dbNewTasks = AppDatabase.getDatabase(activity).addedTasksDao().getAll()
                for (task in dbNewTasks) {
                    apiRepo.addTask(
                        Network.token,
                        TaskForm(
                            task.title,
                            task.description,
                            0,
                            task.deadline,
                            task.categoryID,
                            task.priorityID
                        )
                    )
                }

                val bdEditedTasks = AppDatabase.getDatabase(activity).editedTasksDao().getAll()
                for (task in bdEditedTasks) {
                    apiRepo.editTask(
                        Network.token,
                        task.uid,
                        TaskForm(
                            task.title,
                            task.description,
                            null,
                            task.deadline,
                            task.categoryID,
                            task.priorityID
                        )
                    )
                }
            }
        }

}