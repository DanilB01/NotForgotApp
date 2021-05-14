package ru.tsu.lab3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.tsu.lab3.network.Task
import ru.tsu.lab3.network.TaskForm

@Dao
interface AllTasksDao {
    @Insert
    fun addTask(task: AllTasks)

    @Query("DELETE FROM all_tasks_table")
    fun deleteAll()

    @Query("SELECT * FROM all_tasks_table")
    fun getAll(): Array<AllTasks>

    /*@Query("UPDATE all_tasks_table SET task = :curTask AND taskForm = :curTaskForm WHERE task = :id")
    fun changeValue(curTask: Task, curTaskForm: TaskForm, id: Int)*/
}