package ru.tsu.lab3.database.unused

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.tsu.lab3.database.unused.NoteDB

@Dao
interface NoteDao{
    @Insert
    fun addNote(noteDB: NoteDB)

    @Delete
    fun deleteNote(noteDB: NoteDB)

    @Query("SELECT * FROM note_table WHERE uid LIKE :curUID")
    fun getAll(curUID: Int): List<NoteDB>

    @Query("SELECT * FROM note_table WHERE category LIKE :category AND uid LIKE :uid")
    fun getAllNotesOfCategory(category: String, uid: Int): List<NoteDB>

    @Query("UPDATE note_table SET isDone = :isChecked WHERE uid = :curID AND id = :curNoteID")
    fun updateDoneInfo(curID: Int, curNoteID: Int, isChecked: Boolean)
}