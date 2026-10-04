package com.example.gestioncursos.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R

class HorariosAlumnoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horarios_alumno)

        /*
        supportActionBar?.title = "Mis Horarios"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recycler = findViewById<RecyclerView>(R.id.recyclerHorariosAlumno)

        recycler.layoutManager = LinearLayoutManager(this)

        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val alumnoId = prefs.getInt("usuario_id", 0)


        recycler.adapter = HorarioAdapter(
            db.obtenerHorariosAlumno(alumnoId)
        )

         */
    }

    override fun onSupportNavigateUp(): Boolean {
        findViewById<ImageButton>(R.id.btnBackAlumnoHorario).setOnClickListener {
            startActivity(
                Intent(this, AlumnoDashboardActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
            finish()
        }
        return true
    }
}