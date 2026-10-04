package com.example.gestioncursos.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R
import com.example.gestioncursos.activities.AlumnosInscritosActivity
import com.example.gestioncursos.activities.CrearEditarCursoActivity
import com.example.gestioncursos.adapters.CursoAdapter
import com.example.gestioncursos.models.Curso
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfesorDashboardActivity : AppCompatActivity() {

    private lateinit var tvBienvenidaProfesor: TextView
    private lateinit var tvIniciales: TextView
    private lateinit var btnLogoutProfesor: ImageButton
    private lateinit var tvTotalCursos: TextView
    private lateinit var tvCursosActivos: TextView
    private lateinit var btnMisHorarios: MaterialButton
    private lateinit var rvCursosProfesor: RecyclerView
    private var profesorId = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: CursoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesor_dashboard)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        var share = getSharedPreferences("Sesion_usuario", MODE_PRIVATE)
        profesorId  = share.getString("uid", "") ?: ""
        val nombre  = share.getString("nombre", "Profesor") ?: "Profesor"

        tvBienvenidaProfesor = findViewById<TextView>(R.id.tvBienvenidaProfesor)
        tvIniciales = findViewById<TextView>(R.id.tvIniciales)
        tvTotalCursos = findViewById<TextView>(R.id.tvTotalCursos)
        tvCursosActivos = findViewById<TextView>(R.id.tvCursosActivos)
        btnMisHorarios = findViewById<MaterialButton>(R.id.btnMisHorarios)
        btnLogoutProfesor = findViewById<ImageButton>(R.id.btnLogoutProfesor)
        rvCursosProfesor = findViewById<RecyclerView>(R.id.rvCursosProfesor)

        tvBienvenidaProfesor.text = "Bienvenido ${nombre}"
        tvIniciales.text = obtenerDosIniciales(nombre)

        // Logout
        btnLogoutProfesor.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Deseas salir de la sesión?")
                .setPositiveButton("Sí") { _, _ ->
                    auth.signOut()
                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )
                    finishAffinity()
                }
                .setNegativeButton("No", null)
                .show()
        }
        rvCursosProfesor.layoutManager = LinearLayoutManager(this)
        cargarCursos(profesorId)

    }

    override fun onResume() {
        super.onResume()
        cargarCursos(profesorId)
    }


    fun obtenerDosIniciales(nombreCompleto: String): String {
        if (nombreCompleto.isBlank()) return ""

        return nombreCompleto
            .trim()
            .split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
            .take(2) // Toma únicamente las primeras 2 palabras
            .map { it.first().uppercaseChar() }
            .joinToString("")
    }


    private fun cargarCursos(id: String) {
        if (id.isEmpty()) return
        db.collection("cursos").whereEqualTo("idProfesor", id).whereEqualTo("activo", true).get()
            .addOnSuccessListener { document ->
                val cursosActivos = document.toObjects(Curso::class.java)
                tvTotalCursos.text = "${cursosActivos.size}"
                tvCursosActivos.text = "${cursosActivos.size}"
                if (cursosActivos.isNotEmpty()){
                    adapter = CursoAdapter(cursosActivos){cursoSelec ->
                        val intent = Intent(this, DetalleCursoActivity::class.java).apply {
                            putExtra("EXTRA_CURSO_ID", cursoSelec)
                        }
                        startActivity(intent)
                    }
                    rvCursosProfesor.adapter = adapter
                }else{
                    rvCursosProfesor.adapter = CursoAdapter(emptyList())
                    mostrarMensaje("No tienes cursos asignados")
                }
            }
            .addOnFailureListener { exception ->
                mostrarMensaje("Error al cargar los cursos : ${exception.message}")
            }

    }
    fun mostrarMensaje(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

}