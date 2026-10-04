package com.example.gestioncursos.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestioncursos.R
import com.example.gestioncursos.models.Curso
import com.example.gestioncursos.models.Horario
import com.example.gestioncursos.models.Profesor
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore

class DetalleCursoActivity : AppCompatActivity() {

    private lateinit var tvTituloDetalle: TextView
    private lateinit var tvProfesorDetalle: TextView
    private lateinit var toolbarDetalle: MaterialToolbar
    private lateinit var tvHorarioDetalle: TextView
    private lateinit var tvDuracionDetalle: TextView
    private lateinit var tvAulaDetalle: TextView
    private lateinit var tvDescripcionDetalle: TextView
    private lateinit var btnVolver: MaterialButton
    private lateinit var db : FirebaseFirestore
    private var cursoId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_curso)

        db = FirebaseFirestore.getInstance()
        cursoId = intent.getStringExtra("EXTRA_CURSO_ID")

        // Datos del curso

        toolbarDetalle = findViewById<MaterialToolbar>(R.id.toolbarDetalle)
        tvTituloDetalle = findViewById<TextView>(R.id.tvTituloDetalle)
        tvProfesorDetalle = findViewById<TextView>(R.id.tvProfesorDetalle)
        tvHorarioDetalle = findViewById<TextView>(R.id.tvHorarioDetalle)
        tvDuracionDetalle = findViewById<TextView>(R.id.tvDuracionDetalle)
        tvAulaDetalle = findViewById<TextView>(R.id.tvAulaDetalle)
        tvDescripcionDetalle = findViewById<TextView>(R.id.tvDescripcionDetalle)
        btnVolver = findViewById<MaterialButton>(R.id.btnVolver)

        toolbarDetalle.setNavigationOnClickListener {
            finish()
        }

        if (!cursoId.isNullOrEmpty()){
            cargarCurso(cursoId!!)
        }else{
            mostrarMensaje("Error: ID de curso no encontrado")
            finish()
        }

        btnVolver.setOnClickListener {
            startActivity(
                Intent(this, AlumnoDashboardActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
            finish()
        }

    }

    private fun cargarCurso(curso: String){
        if (curso.isEmpty()) return
        db.collection("cursos").document(curso).get()
            .addOnSuccessListener { document ->
                if (document.exists()){
                    val curso = document.toObject(Curso::class.java)
                    if (curso != null){
                        tvTituloDetalle.text = curso.titulo
                        tvDescripcionDetalle.text = curso.descripcion
                        val horario = curso.horario
                        val profesor = curso.idProfesor
                        if (horario.isNotEmpty()){
                            cargarHorario(horario)
                        }else{
                            mostrarMensaje("Horario no asignado")
                        }
                        if (profesor.isNotEmpty()){
                            cargarNombreProfesor(profesor)
                        }
                    }
                }
            }
            .addOnFailureListener {
                mostrarMensaje("Error al cargar curso")
            }
    }

    private fun cargarNombreProfesor(profesor: String){
        db.collection("profesor").document(profesor).get()
            .addOnSuccessListener { docProf ->
                if (docProf.exists()){
                    val nombre = docProf.getString("nombre") ?: ""
                    val apellido = docProf.getString("apellido") ?: ""
                    tvProfesorDetalle.text = "Prof. $nombre $apellido".trim()
                }else{
                    tvProfesorDetalle.text = "Prof. Desconocido"
                }
            }
            .addOnFailureListener {
                tvProfesorDetalle.text = "Prof. No disponible"
            }
    }

    private fun cargarHorario(idHorario: String){
        db.collection("horario").document(idHorario).get()
            .addOnSuccessListener { horarioSelec ->
                if (horarioSelec.exists()){
                    val horario = horarioSelec.toObject(Horario::class.java)
                    if (horario != null){
                        tvDuracionDetalle.text = "${horario.horaInicio} - ${horario.horaFin}"
                        tvHorarioDetalle.text = horario.dias
                        tvAulaDetalle.text = horario.aula
                    }
                }else{
                    tvHorarioDetalle.text = "Horario no encontrado"
                }
            }
            .addOnFailureListener {
                mostrarMensaje("Error al cargar horario")
            }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}