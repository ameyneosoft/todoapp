package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CompletedTaskAdapter (val context: Context,var list : List<Task>): RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder>(){
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val editButton : ImageButton = view.findViewById(R.id.edit_button)
        val title : TextView = view.findViewById(R.id.todo_title)
        val subtitle : TextView = view.findViewById(R.id.todo_subtitle)
        val deleteButton : ImageButton = view.findViewById(R.id.delete_button)
        val completeButton : ImageButton = view.findViewById(R.id.complete_button)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.editButton.visibility = View.INVISIBLE
        holder.deleteButton.visibility = View.INVISIBLE
        holder.completeButton.visibility = View.INVISIBLE
        holder.title.setText(list[position].title)
        holder.subtitle.setText(list[position].subtitle)

    }
}