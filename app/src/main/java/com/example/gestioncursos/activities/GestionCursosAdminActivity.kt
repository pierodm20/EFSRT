package com.example.gestioncursos.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R
import com.example.gestioncursos.activities.CrearEditarCursoActivity
import com.example.gestioncursos.adapters.CursoAdapter
import com.example.gestioncursos.models.Curso
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore

class GestionCursosAdminActivity : AppCompatActivity() {
    private lateinit var btnBackAdmin: ImageButton
    private lateinit var tvTotalAdmin: TextView
    private lateinit var etFiltroAdmin: EditText
    private lateinit var tabsAdmin: TabLayout
    private lateinit var rvCursosAdmin: RecyclerView
    private lateinit var fabCrearCursoAdmin: FloatingActionButton
    private lateinit var adapter: CursoAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_cursos_admin)
        db = FirebaseFirestore.getInstance()
        btnBackAdmin = findViewById<ImageButton>(R.id.btnBackAdmin)
        tvTotalAdmin = findViewById<TextView>(R.id.tvTotalAdmin)
        etFiltroAdmin = findViewById<EditText>(R.id.etFiltroAdmin)
        tabsAdmin = findViewById<TabLayout>(R.id.tabsAdmin)
        rvCursosAdmin = findViewById<RecyclerView>(R.id.rvCursosAdmin)
        fabCrearCursoAdmin = findViewById<FloatingActionButton>(R.id.fabCrearCursoAdmin)

        rvCursosAdmin.layoutManager = LinearLayoutManager(this)

        // FAB crear curso
        fabCrearCursoAdmin.setOnClickListener {
            val intent = Intent(this, CrearEditarCursoActivity::class.java)
            startActivity(intent)
        }

        // Botón volver
        btnBackAdmin.setOnClickListener {
            finish()
        }

        // Tabs: Todos / Activos / Inactivos
        val tabs = findViewById<TabLayout>(R.id.tabsAdmin)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> cargarCursos()
                    1 -> cargarCursosPorEstado(soloActivos = true)
                    2 -> cargarCursosPorEstado(soloActivos = false)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        cargarCursos()
    }

    override fun onResume() {
        super.onResume()
        cargarCursos()
    }


    private fun cargarCursos() {
        db.collection("cursos").get()
            .addOnSuccessListener { document ->
                val cursos = document.toObjects(Curso::class.java)
                tvTotalAdmin.text = "${cursos.size}"
                if (cursos.isNotEmpty()){
                    adapter = CursoAdapter(cursos)
                    rvCursosAdmin.adapter = adapter
                }else{
                    rvCursosAdmin.adapter = CursoAdapter(emptyList())
                    mostrarMensaje("No se encontraron cursos")
                }
            }
            .addOnFailureListener { exception ->
                mostrarMensaje("Error al cargar cursos: ${exception.message}")
            }
    }

    private fun cargarCursosPorEstado(soloActivos: Boolean) {
        db.collection("cursos").whereEqualTo("activo", soloActivos).get()
            .addOnSuccessListener { document ->
                val cursos = document.toObjects(Curso::class.java)
                if (cursos.isNotEmpty()){
                    adapter = CursoAdapter(cursos)
                    rvCursosAdmin.adapter = adapter
                }else{
                    rvCursosAdmin.adapter = CursoAdapter(emptyList())
                    mostrarMensaje("No se encontraron cursos")
                }
            }
            .addOnFailureListener { exception ->
                mostrarMensaje("Error al cargar cursos: ${exception.message}")
            }
    }

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}