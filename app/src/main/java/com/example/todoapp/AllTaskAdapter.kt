package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AllTaskAdapter(
    val context: Context,
    var list: List<Task>,
    val onDelete: (Task) -> Unit,
    val onComplete: (Int) -> Unit,
) : RecyclerView.Adapter<AllTaskAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editButton: ImageButton = view.findViewById(R.id.edit_button)
        val title: TextView = view.findViewById(R.id.todo_title)
        val subtitle: TextView = view.findViewById(R.id.todo_subtitle)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
        val completeButton: ImageButton = view.findViewById(R.id.complete_button)
        val constLayout: ConstraintLayout = view.findViewById(R.id.todo_card_constraint_layout)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.editButton.setOnClickListener {
            val intent = Intent(context, EditTask::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("id", list[position].uid)
            context.startActivity(intent)
        }
        holder.title.text = list[position].title
        holder.subtitle.text = list[position].subtitle
        holder.deleteButton.setOnClickListener {
            onDelete(list[position])
        }
        holder.completeButton.setOnClickListener {
            onComplete(list[position].uid)
        }
        if (list[position].isCompleted) {
            holder.constLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context, R.color.completed_color
                )
            )
            holder.completeButton.visibility = View.GONE
            holder.editButton.visibility = View.GONE
        }else{
            holder.constLayout.background = null
            holder.completeButton.visibility = View.VISIBLE
            holder.editButton.visibility = View.VISIBLE
        }
    }
}