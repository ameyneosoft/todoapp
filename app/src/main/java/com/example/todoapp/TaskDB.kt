package com.example.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDB : RoomDatabase() {
 abstract val taskDao : TaskDao
 companion object {
     var taskInstance :TaskDB?=null
     fun getInstance(context: Context) : TaskDB{
         if(taskInstance == null){
             taskInstance = Room.databaseBuilder(context,TaskDB ::class.java,"TaskDB").allowMainThreadQueries().build()
         }
         return taskInstance!!
     }
 }
}