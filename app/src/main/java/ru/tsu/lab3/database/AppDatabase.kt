package ru.tsu.lab3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [/*UserDB::class, NoteDB::class, CategoryDB::class*/], version = 1)
@Database(entities = [AllTasks::class, AddedTasks::class, EditedTasks::class, CategoryForDB::class, PriorityForDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /*abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao*/

    abstract fun allTasksDao(): AllTasksDao
    abstract fun addedTasksDao(): AddedTasksDao
    abstract fun editedTasksDao(): EditedTasksDao
    abstract fun categoriesDao(): CategoryForDBDao
    abstract fun priorityDao(): PriorityForDBDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance =
                INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}