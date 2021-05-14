package ru.tsu.lab3.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tsu.lab3.network.Category

@Entity(tableName = "categories_table")
data class CategoryForDB (
    //val category: Category,
    val name: String,
    val uid: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)