package ru.tsu.lab3.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tsu.lab3.network.Category
import ru.tsu.lab3.network.Priority
import ru.tsu.lab3.network.Task
import ru.tsu.lab3.network.TaskForm

@Entity(tableName = "all_tasks_table")
data class AllTasks(
    //val task: Task,
    //val taskForm: TaskForm,
    val title: String,
    val description: String?,
    val done: Int,
    val deadline: Int,
    val categoryName: String,
    val priorityName: String,
    val priorityColor: String,
    val created: Int,
    //val uid: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)