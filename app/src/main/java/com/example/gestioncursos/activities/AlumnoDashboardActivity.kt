package com.example.gestioncursos.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R
import com.example.gestioncursos.activities.BuscarCursosActivity
import com.example.gestioncursos.adapters.CursoAdapter
import com.example.gestioncursos.models.Curso

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AlumnoDashboardActivity : AppCompatActivity() {
    private lateinit var rvCursos: RecyclerView
    private lateinit var tvBienvenida: TextView
    private lateinit var tvCursosCount : TextView
    private lateinit var ivCerrarSesion : ImageView
    private lateinit var tvProximaClase: TextView
    private lateinit var adapter: CursoAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private var alumnoId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumno_dashboard)
        val share = getSharedPreferences("Sesion_usuario", Context.MODE_PRIVATE)
        val nombre = share.getString("nombre", "Alumno") ?: "Alumno"
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        alumnoId = share.getString("uid", "") ?: ""
        tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        tvCursosCount = findViewById<TextView>(R.id.tvCursosCount)
        rvCursos = findViewById<RecyclerView>(R.id.rvMisCursos)
        ivCerrarSesion = findViewById<ImageView>(R.id.ivCerrarSesion)

        tvBienvenida.text = "¡Hola, $nombre! 👋"
        rvCursos.layoutManager = LinearLayoutManager(this)
        cargarMisCursos(alumnoId)

        ivCerrarSesion.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        cargarMisCursos(alumnoId)
    }

    private fun cargarMisCursos(alumno : String) {
        if (alumno.isEmpty()) return
        db.collection("profesor").get()
            .addOnSuccessListener { profDocs ->
                val mapaProfesores = mutableMapOf<String, String>()
                for (doc in profDocs) {
                    val nombreCompleto = "${doc.getString("nombre") ?: ""} ${doc.getString("apellido") ?: ""}".trim()
                    mapaProfesores[doc.id] = nombreCompleto
                }

                // Luego consultamos los cursos del alumno
                db.collection("cursos").whereArrayContains("alumnos", alumnoId).get()
                    .addOnSuccessListener { cursoDocs ->
                        val cursos = cursoDocs.toObjects(Curso::class.java)

                        tvCursosCount.text = "${cursos.size} inscritos"
                        if (cursos.isNotEmpty()) {
                            // Le pasamos el mapa con los nombres al Adapter
                            adapter = CursoAdapter(cursos, mapaProfesores){ cursoSelec ->
                                val intent = Intent(this, DetalleCursoActivity::class.java).apply {
                                    putExtra("EXTRA_CURSO_ID", cursoSelec)
                                }
                                startActivity(intent)
                            }
                            rvCursos.adapter = adapter
                        } else {
                            rvCursos.adapter = CursoAdapter(emptyList(), emptyMap())
                            mostrarMensaje("No tienes cursos inscritos")
                        }
                    }
            }
    }

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}