package com.example.tareas

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tareas.modelo.Task
import com.example.tareas.adapters.TaskAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()
    private val filteredTasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración del RecyclerView
        recyclerViewTasks = findViewById(R.id.recycler_view_tasks)
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)

        // Inicialización del adaptador y asignación al RecyclerView
        taskAdapter = TaskAdapter(filteredTasks)
        recyclerViewTasks.adapter = taskAdapter

        // Configuración del Spinner de filtro
        val spinnerFilter: Spinner = findViewById(R.id.spinner_filter)
        ArrayAdapter.createFromResource(
            this,
            R.array.filter_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFilter.adapter = adapter
        }

        // Manejar selección de filtro
        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                applyFilter(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Configuración del botón para agregar una tarea
        findViewById<Button>(R.id.button_add_task).setOnClickListener {
            openFragment(AddTaskFragment())
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Función para aplicar el filtro
    private fun applyFilter(filterOption: Int) {
        filteredTasks.clear()
        when (filterOption) {
            0 -> filteredTasks.addAll(taskList) // Todas las tareas
            1 -> filteredTasks.addAll(taskList.filter { it.isCompleted() }) // Solo completadas
            2 -> filteredTasks.addAll(taskList.filter { !it.isCompleted() }) // Solo no completadas
        }
        taskAdapter.notifyDataSetChanged()
    }

    fun addTask(task: Task) {
        taskList.add(task)
        applyFilter(findViewById<Spinner>(R.id.spinner_filter).selectedItemPosition)
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
        applyFilter(findViewById<Spinner>(R.id.spinner_filter).selectedItemPosition)
    }
}
