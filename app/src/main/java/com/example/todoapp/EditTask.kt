package com.example.todoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.databinding.ActivityAddTaskBinding
import com.example.todoapp.databinding.ActivityEditTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class EditTask : AppCompatActivity() {
    private val binding: ActivityEditTaskBinding by lazy {
        ActivityEditTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.editToolbar.toolbarHeading.text = "Edit Task"
        binding.editToolbar.backButton.setOnClickListener {
            this.finish()
        }
        binding.cancelButton.setOnClickListener {
            this.finish()
        }

        val editId = intent.getIntExtra("id", 0)
        lifecycleScope.launch(Dispatchers.IO) {
            val taskDao = TaskDB.getInstance(this@EditTask).taskDao
            val editTask: Task = taskDao.getById(editId)
            lifecycleScope.launch(Dispatchers.Main) {
                binding.editTitleInput.setText(editTask.title)
                binding.editDetailInput.setText(editTask.subtitle)
            }
        }

        binding.updateButton.setOnClickListener {
            val taskDao = TaskDB.getInstance(this@EditTask).taskDao
            val editTask: Task = taskDao.getById(editId)
            lifecycleScope.launch(Dispatchers.IO) {
                var updatedTask = Task(
                    editId,
                    binding.editTitleInput.text.toString(),
                    binding.editDetailInput.text.toString(),
                    editTask.date,
                    editTask.isCompleted
                )
                taskDao.update(updatedTask)
                lifecycleScope.launch(Dispatchers.Main) {
                    this@EditTask.finish()
                }
            }

        }
    }
}