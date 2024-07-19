package com.example.todoapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.databinding.ActivityAddTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime

class AddTask : AppCompatActivity() {
    private  val binding : ActivityAddTaskBinding by lazy {
        ActivityAddTaskBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.addTaskToolbar.toolbarHeading.text = "Add Task"
        binding.addTaskToolbar.backButton.setOnClickListener {
            this.finish()
        }
        binding.addTaskButton.setOnClickListener {
            val title = binding.addTitleInput.text.toString()
            val subTitle = binding.addDetailInput.text.toString()
            val taskDao = TaskDB.getInstance(this).taskDao
            val date = System.currentTimeMillis()
            CoroutineScope(Dispatchers.IO).launch {
                taskDao.insert(Task(title = title, subtitle = subTitle, isCompleted = false, date = date))
                Log.d("TASK","Task added successfully")
                this@AddTask.finish()
            }
        }
    }

    fun convertToLong(timestamp: Timestamp) : Long{
        return timestamp.time
    }
}

