package ru.tsu.lab3.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.tsu.lab3.network.TaskForm

@Entity(tableName = "edited_tasks_table")
data class EditedTasks (

    val uid: Int,
    //val task: TaskForm
    val title: String?,
    val description: String?,
    var done: Int?,
    val deadline: Int?,
    val categoryID: Int?,
    val priorityID: Int?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)