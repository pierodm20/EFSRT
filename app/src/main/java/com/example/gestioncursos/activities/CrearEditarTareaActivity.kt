package com.example.gestioncursos.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestioncursos.R
import com.example.gestioncursos.models.Tarea

class CrearEditarTareaActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_tarea)
        /*

        db = DatabaseHelper(this)

        val edtTitulo = findViewById<EditText>(R.id.edtTituloTarea)
        val edtDescripcion = findViewById<EditText>(R.id.edtDescripcionTarea)
        val edtCurso = findViewById<EditText>(R.id.edtCursoTarea)
        val edtFecha = findViewById<EditText>(R.id.edtFechaLimiteTarea)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarTarea)

        btnGuardar.setOnClickListener {

            val tarea = Tarea(
                titulo = edtTitulo.text.toString(),
                descripcion = edtDescripcion.text.toString(),
                curso = edtCurso.text.toString(),
                fechaLimite = edtFecha.text.toString(),
                estado = "Pendiente"
            )

            db.insertarTarea(tarea)

            Toast.makeText(this, "Tarea registrada", Toast.LENGTH_SHORT).show()

            finish()
        }

         */
    }
}