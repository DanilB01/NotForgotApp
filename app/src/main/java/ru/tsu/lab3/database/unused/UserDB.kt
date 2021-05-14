package ru.tsu.lab3.database.unused

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDB (
    val name: String,
    val email: String,
    val password: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)