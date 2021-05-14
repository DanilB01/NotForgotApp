package ru.tsu.lab3.database.unused

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryDB (
    val uid: Int,
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)