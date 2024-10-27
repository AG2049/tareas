package com.example.tareas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tareas.modelo.Task

class AddTaskFragment : Fragment() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonSaveTask: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        editTextTitle = view.findViewById(R.id.edit_text_title)
        editTextDescription = view.findViewById(R.id.edit_text_description)
        buttonSaveTask = view.findViewById(R.id.button_save_task)

        buttonSaveTask.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val task = Task((0..1000).random(), title, description, false)
                (activity as? MainActivity)?.addTask(task)
                Toast.makeText(context, "Tarea agregada", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack() // Regresa a la pantalla anterior
            } else {
                Toast.makeText(context, "El título y la descripción no deben estar vacíos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
