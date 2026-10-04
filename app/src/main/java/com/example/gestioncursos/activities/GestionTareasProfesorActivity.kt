package com.example.gestioncursos.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R
import com.example.gestioncursos.adapters.TareaAdapter
import com.google.android.material.button.MaterialButton

class GestionTareasProfesorActivity : AppCompatActivity() {


    private lateinit var rvTareas: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_tareas_profesor)

        /*
        db = DatabaseHelper(this)
        rvTareas = findViewById(R.id.rvTareasProfesor)
        rvTareas.layoutManager = LinearLayoutManager(this)

        findViewById<MaterialButton>(R.id.btnNuevaTarea).setOnClickListener {
            startActivity(Intent(this, CrearEditarTareaActivity::class.java))
        }

        cargarTareas()

         */
    }

    override fun onResume() {
        super.onResume()

    }

    private fun cargarTareas() {
        /*
        val tareas = db.listarTareas()

        val adapter = TareaAdapter(
            tareas,
            onCompletarClick = { tarea ->
                db.marcarTareaCompletada(tarea.id)
                cargarTareas()
            },
            onEditarClick = { tarea ->
                startActivity(
                    Intent(this, CrearEditarTareaActivity::class.java).apply {
                        putExtra("tarea_id", tarea.id)
                        putExtra("modo", "editar")
                    }
                )
            },
            onEliminarClick = { tarea ->
                db.eliminarTarea(tarea.id)
                cargarTareas()
            }
        )

        rvTareas.adapter = adapter

         */
    }
}