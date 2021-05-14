package ru.tsu.lab3.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tsu.lab3.network.Priority

@Entity(tableName = "priorities_table")
class PriorityForDB(
    //val priority: Priority,
    val name: String,
    val color: String,
    val uid: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)