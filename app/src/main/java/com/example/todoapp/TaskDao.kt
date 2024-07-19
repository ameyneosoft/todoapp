package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface TaskDao {
    @Query("SELECT * from tasks_table")
    fun getAll() : LiveData<List<Task>>

    @Query("SELECT * from tasks_table where uid = :id")
    fun getById(id : Int) : Task

    @Query("SELECT * from tasks_table where isCompleted = 1")
    fun getCompletedTasks() : List<Task>

    @Insert
    fun insert(task : Task)

    @Delete
    fun delete(task: Task)

    @Upsert
    fun update(task : Task)

    @Query("UPDATE tasks_table SET isCompleted = 1 where uid = :id ")
    fun completeTask(id : Int)

}