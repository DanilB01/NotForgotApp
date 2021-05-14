package ru.tsu.lab3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryForDBDao {
    @Query("SELECT * FROM categories_table")
    fun getAll(): Array<CategoryForDB>

    @Insert
    fun add(cat: CategoryForDB)
}