package com.example.gestioncursos.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R

class HorariosProfesorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horarios_profesor)

        /*
        findViewById<ImageButton>(R.id.btnBackProfesorHorario).setOnClickListener {
            startActivity(
                Intent(this, ProfesorDashboardActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
            finish()
        }

        val recycler = findViewById<RecyclerView>(R.id.recyclerHorariosProfesor)

        recycler.layoutManager = LinearLayoutManager(this)

        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val profesorId = prefs.getInt("usuario_id", 0)


        recycler.adapter = HorarioAdapter(
            db.obtenerHorariosProfesor(profesorId)
        )

         */
    }
}