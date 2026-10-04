package com.example.gestioncursos.activities

import com.example.gestioncursos.R

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.adapters.TareaAdapter

class TareasAlumnoActivity : AppCompatActivity() {

    private lateinit var rvTareas: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareas_alumno)

        /*
        db = DatabaseHelper(this)
        rvTareas = findViewById(R.id.rvTareasAlumno)

        rvTareas.layoutManager = LinearLayoutManager(this)
        cargarTareas()

         */
    }

    /*
    private fun cargarTareas() {
        val tareas = db.listarTareas()

        val adapter = TareaAdapter(
            tareas,
            onCompletarClick = { tarea ->
                db.marcarTareaCompletada(tarea.id)
                Toast.makeText(this, "Tarea completada", Toast.LENGTH_SHORT).show()
                cargarTareas()
            },
            onEditarClick = { },
            onEliminarClick = { }
        )

        rvTareas.adapter = adapter
    }

     */
}