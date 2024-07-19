package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ActivityAllTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllTaskActivity : AppCompatActivity() {

    lateinit var allTaskViewModel: AllTaskViewModel
    lateinit var allTaskAdapter: AllTaskAdapter
    lateinit var toDoListRecyclerView: RecyclerView
    private val binding: ActivityAllTaskBinding by lazy {
        ActivityAllTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_task)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val taskDao = TaskDB.getInstance(this).taskDao

        allTaskViewModel = ViewModelProvider(
            this, AllTaskViewModelFactory(taskDao)
        ).get(AllTaskViewModel::class.java)

        val li: List<Task> = mutableListOf()
        toDoListRecyclerView = binding.todosListRecyclerView
        toDoListRecyclerView.layoutManager = LinearLayoutManager(baseContext)
        allTaskAdapter = AllTaskAdapter(baseContext, listOf(), deleteTask, onComplete)
        toDoListRecyclerView.adapter = allTaskAdapter
        allTaskViewModel.allTaskLiveData.observe(this) {
            allTaskAdapter.list = it
            Log.d("observe", "list changed $it")
            allTaskAdapter.notifyDataSetChanged()
        }

        binding.bottomNavigationMenu.findViewById<View>(R.id.completed_button).setOnClickListener {
            val intent = Intent(baseContext, CompletedTask::class.java)
            startActivity(intent)
        }

        binding.addTaskFab.setOnClickListener {
            val intent = Intent(baseContext, AddTask::class.java)
            startActivity(intent)
        }
    }


    val deleteTask: (Task) -> Unit = { deleteTask: Task ->
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setPositiveButton("Delete") { _, _ ->
            allTaskViewModel.deleteTask(deleteTask)
        }
        builder.setMessage("Are you sure you want to delete")
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    val onComplete: (Int) -> Unit = { uid ->
        val taskDao = TaskDB.getInstance(this).taskDao
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setPositiveButton("Complete") { _, _ ->
            allTaskViewModel.completeTask(uid)
        }
        builder.setMessage("Are you sure you want to mark as Complete")
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }
}