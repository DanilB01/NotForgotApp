package ru.tsu.lab3.database.unused

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao{
    @Insert
    fun addCategory(categoryDB: CategoryDB)

    @Delete
    fun deleteCategory(categoryDB: CategoryDB)

    @Query("SELECT * FROM category_table WHERE uid LIKE :curUID")
    fun getAll(curUID: Int): List<CategoryDB>

}