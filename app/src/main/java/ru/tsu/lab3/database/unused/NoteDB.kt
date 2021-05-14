package ru.tsu.lab3.database.unused

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteDB (
    val uid: Int,
    val category: String,
    val priority: Int,
    val title: String,
    val description: String,
    val date: String,
    val isDone: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)