package com.example.todoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.todoapp.databinding.ActivityCompletedTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompletedTask : AppCompatActivity() {
    private val binding : ActivityCompletedTaskBinding by lazy {
        ActivityCompletedTaskBinding.inflate(layoutInflater)
    }

    private lateinit var completedTaskAdapter : CompletedTaskAdapter
    private lateinit var completedTaskRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.completedToolbar.toolbarHeading.text = "Completed Task"
        binding.completedToolbar.backButton.setOnClickListener {
            this.finish()
        }
        val list : List<Task> = mutableListOf()
        completedTaskRecyclerView = binding.completedTaskRecyclerView
        completedTaskRecyclerView.layoutManager = LinearLayoutManager(baseContext)
        completedTaskAdapter = CompletedTaskAdapter(baseContext ,list)
        completedTaskRecyclerView.adapter = completedTaskAdapter
        fetchCompletedTasks()

    }
    fun fetchCompletedTasks () {
        val taskDao = TaskDB.getInstance(this).taskDao
        lifecycleScope.launch (Dispatchers.IO){
            val li : List<Task> = taskDao.getCompletedTasks()
            Log.d("TASK","Completed Task fetched successfully")
            lifecycleScope.launch(Dispatchers.Main) {
                completedTaskAdapter.list = li
                completedTaskAdapter.notifyDataSetChanged()
            }
        }
    }
}