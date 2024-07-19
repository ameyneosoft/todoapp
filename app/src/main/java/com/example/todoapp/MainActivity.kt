package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
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

        binding.loginButton.setOnClickListener {
            val enteredEmail = binding.emailInput.text
            val enteredPass = binding.passwordInput.text
            if(enteredPass.contentEquals(Credentials.password) &&  enteredEmail.contentEquals(Credentials.email)){
                Toast.makeText(this,"Correct Credentials",Toast.LENGTH_SHORT).show()
                val intent = Intent(baseContext,AllTaskActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK xor Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Incorrect Credentials",Toast.LENGTH_SHORT).show()

            }
        }

    }
    object Credentials {
        const val email : String = "a"
        const val password : String = "p"
    }
}