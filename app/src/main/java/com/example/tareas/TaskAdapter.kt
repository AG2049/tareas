package com.example.tareas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tareas.MainActivity
import com.example.tareas.modelo.Task
import com.example.tareas.R

class TaskAdapter(private val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.textViewTitle.text = task.title
        holder.checkBoxCompleted.isChecked = task.isCompleted()
        holder.checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
            task.setCompleted(isChecked)
        }

        holder.iconDeleteTask.setOnClickListener {
            (holder.itemView.context as? MainActivity)?.deleteTask(task)
        }
    }

    override fun getItemCount(): Int = tasks.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val checkBoxCompleted: CheckBox = itemView.findViewById(R.id.check_box_completed)
        val iconDeleteTask: ImageView = itemView.findViewById(R.id.icon_delete_task)
    }
}
