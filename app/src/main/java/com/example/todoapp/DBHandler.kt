package com.example.todoapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DBHandler {

    fun getDb(context : Context) : TaskDB {
        return Room.databaseBuilder(context,TaskDB ::class.java,"TaskDB").build()
    }
}