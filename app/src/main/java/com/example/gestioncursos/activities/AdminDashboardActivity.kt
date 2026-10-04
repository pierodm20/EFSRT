package com.example.gestioncursos.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.gestioncursos.R
import com.example.gestioncursos.activities.GestionCursosAdminActivity
import com.example.gestioncursos.activities.LoginActivity
import com.example.gestioncursos.models.Curso
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var tvIniciales: TextView
    private lateinit var tvNombreAdmin: TextView
    private lateinit var tvTotalCursos: TextView
    private lateinit var tvCursosActivos: TextView
    private lateinit var tvTotalProfesores: TextView
    private lateinit var cardGestionCursos: MaterialCardView
    private lateinit var cardCerrarSesion: MaterialCardView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var adminId : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        tvIniciales = findViewById<TextView>(R.id.tvIniciales)
        tvNombreAdmin = findViewById<TextView>(R.id.tvNombreAdmin)
        tvTotalCursos = findViewById<TextView>(R.id.tvTotalCursos)
        tvCursosActivos = findViewById<TextView>(R.id.tvCursosActivos)
        tvTotalProfesores = findViewById<TextView>(R.id.tvTotalProfesores)
        cardGestionCursos = findViewById<MaterialCardView>(R.id.cardGestionCursos)
        cardCerrarSesion = findViewById<MaterialCardView>(R.id.cardCerrarSesion)

        val share = getSharedPreferences("Sesion_usuario", MODE_PRIVATE)
        adminId = share.getString("uid", "") ?: ""
        val nombre = share.getString("nombre", "Admin") ?: "Admin"

        tvNombreAdmin.text = "Bienvenido ${nombre}"
        tvIniciales.text = obtenerDosIniciales(nombre)
        cargarCursos()
        cargarProfesores()

        cardGestionCursos.setOnClickListener {
            val intent = Intent(this, GestionCursosAdminActivity::class.java)
            startActivity(intent)
        }


        cardCerrarSesion.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

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

    fun cargarCursos(){
        db.collection("cursos").get()
            .addOnSuccessListener { document ->
                val cursos = document.size()
                tvTotalCursos.text = "${cursos}"
            }
            .addOnFailureListener {exception ->
                mostrarMensaje("Error al cargar los cursos: ${exception.message}")
            }

        db.collection("cursos").whereEqualTo("activo", true).get()
            .addOnSuccessListener { doc ->
                val cursosActivos = doc.size()
                tvCursosActivos.text = "${cursosActivos}"
            }
            .addOnFailureListener { exception ->
                mostrarMensaje("Error al cargar los cursos activos: ${exception.message}")
            }
    }

    fun cargarProfesores(){
        db.collection("profesor").get()
            .addOnSuccessListener { document ->
                val profesor = document.size()
                tvTotalProfesores.text = "${profesor}"
            }
            .addOnFailureListener {exception ->
                mostrarMensaje("Error al cargar los cursos: ${exception.message}")
            }
    }

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}