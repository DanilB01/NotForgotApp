package ru.tsu.lab3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EditedTasksDao {
    @Insert
    fun addTask(task: EditedTasks)

    @Query("DELETE FROM edited_tasks_table")
    fun deleteAll()

    @Query("SELECT * FROM edited_tasks_table")
    fun getAll(): Array<EditedTasks>
}