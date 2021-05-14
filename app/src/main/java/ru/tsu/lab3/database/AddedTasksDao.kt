package ru.tsu.lab3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AddedTasksDao {
    @Insert
    fun addTask(task: AddedTasks)

    @Query("DELETE FROM added_tasks_table")
    fun deleteAll()

    @Query("SELECT * FROM added_tasks_table")
    fun getAll(): Array<AddedTasks>
}