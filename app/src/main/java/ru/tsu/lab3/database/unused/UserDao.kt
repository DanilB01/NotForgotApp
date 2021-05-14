package ru.tsu.lab3.database.unused

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun addUser(userDB: UserDB)

    @Delete
    fun deleteUser(userDB: UserDB)

    @Query("SELECT * FROM user_table")
    fun getAll(): List<UserDB>

    @Query("SELECT * FROM user_table WHERE email LIKE :emailInput AND password LIKE :passwordInput LIMIT 1")
    fun findByEmailAndPassword(emailInput: String, passwordInput: String): UserDB

    @Query("SELECT id FROM user_table WHERE email LIKE :emailInput AND password LIKE :passwordInput LIMIT 1")
    fun getUserID(emailInput: String, passwordInput: String) : Int

}