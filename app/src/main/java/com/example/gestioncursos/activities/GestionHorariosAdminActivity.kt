package com.example.gestioncursos.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R
import com.example.gestioncursos.models.Curso
import com.example.gestioncursos.models.Horario

class GestionHorariosAdminActivity : AppCompatActivity() {


    private lateinit var spinnerDia: Spinner
    private lateinit var spinnerCursos: Spinner
    private lateinit var etHoraInicio: EditText
    private lateinit var etHoraFin: EditText
    private lateinit var etAula: EditText
    private lateinit var btnGuardar: Button
    private lateinit var recyclerHorarios: RecyclerView
    private lateinit var btnBack: ImageButton

    private var cursos = mutableListOf<Curso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_horarios_admin)

        /*
        spinnerDia = findViewById(R.id.spinnerDia)
        spinnerCursos = findViewById(R.id.spinnerCursos)
        etHoraInicio = findViewById(R.id.etHoraInicio)
        etHoraFin = findViewById(R.id.etHoraFin)
        etAula = findViewById(R.id.etAula)
        btnGuardar = findViewById(R.id.btnGuardarHorario)
        recyclerHorarios = findViewById(R.id.recyclerHorarios)

        btnBack = findViewById(R.id.btnBackHorariosAdmin)

        btnBack.setOnClickListener {
            finish()
        }

        db = DatabaseHelper(this)


        val dias = listOf(
            "Lunes",
            "Martes",
            "Miércoles",
            "Jueves",
            "Viernes",
            "Sábado"
        )

        spinnerDia.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            dias
        )

        // Cargar cursos
        cursos = db.obtenerTodosCursos().toMutableList()

        val nombresCursos = cursos.map { it.titulo }

        val adapterCursos = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            nombresCursos
        )

        adapterCursos.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerCursos.adapter = adapterCursos

        recyclerHorarios.layoutManager =
            LinearLayoutManager(this)

        cargarHorarios()

        btnGuardar.setOnClickListener {

            if (cursos.isEmpty()) {
                Toast.makeText(
                    this,
                    "No hay cursos registrados",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (
                etHoraInicio.text.isBlank() ||
                etHoraFin.text.isBlank() ||
                etAula.text.isBlank()
            ) {
                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val cursoId =
                cursos[spinnerCursos.selectedItemPosition].id

            val horario = Horario(
                cursoId = cursoId,
                dia = spinnerDia.selectedItem.toString(),
                horaInicio = etHoraInicio.text.toString(),
                horaFin = etHoraFin.text.toString(),
                aula = etAula.text.toString()
            )

            db.insertarHorario(horario)

            Toast.makeText(
                this,
                "Horario registrado",
                Toast.LENGTH_SHORT
            ).show()

            etHoraInicio.text.clear()
            etHoraFin.text.clear()
            etAula.text.clear()

            cargarHorarios()
        }

        supportActionBar?.title = "Gestión de Horarios"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

         */
    }


    /*
    private fun cargarHorarios() {
        recyclerHorarios.adapter =
            HorarioAdapter(db.obtenerHorarios())
    }

    override fun onSupportNavigateUp(): Boolean {
        findViewById<ImageButton>(R.id.btnBackHorariosAdmin).setOnClickListener {
            startActivity(
                Intent(this, AdminDashboardActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
            finish()
        }
        finish()
        return true
    }

     */
}