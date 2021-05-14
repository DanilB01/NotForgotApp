package ru.tsu.lab3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PriorityForDBDao {
    @Query("SELECT * FROM priorities_table")
    fun getAll(): List<PriorityForDB>

    @Insert
    fun add(pr: PriorityForDB)
}