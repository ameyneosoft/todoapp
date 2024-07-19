package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllTaskViewModel(val taskDao: TaskDao) : ViewModel(){
    var allTaskLiveData : LiveData<List<Task>> = taskDao.getAll()

    fun deleteTask(task: Task ){
        viewModelScope.launch(Dispatchers.IO) {
            val job = async { taskDao.delete(task) }
            job.await()
        }
    }

    fun completeTask(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val job = async { taskDao.completeTask(id) }
            job.await()
        }
    }

}