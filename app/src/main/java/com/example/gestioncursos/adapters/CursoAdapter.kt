package com.example.gestioncursos.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R
import com.example.gestioncursos.models.Curso
import com.google.android.material.chip.Chip


class CursoAdapter(
    private var cursos: List<Curso>,
    private val profesoresMap: Map<String, String> = emptyMap(),
    private val onClick : (String) -> Unit = {}
) : RecyclerView.Adapter<CursoAdapter.CursoViewHolder>() {
    inner class CursoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.cardCurso)
        val tvTitulo: TextView = view.findViewById(R.id.tvTituloCurso)
        val tvProfesor: TextView = view.findViewById(R.id.tvProfesorCurso)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionCurso)
        val chipEstado: Chip = view.findViewById(R.id.chipEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_curso, parent, false)
        return CursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = cursos[position]
        holder.tvTitulo.text = curso.titulo
        val nombreProfesor = profesoresMap[curso.idProfesor] ?: "Profesor no asignado"
        holder.tvProfesor.text = "Prof. ${nombreProfesor}"
        holder.tvDescripcion.text = curso.descripcion
        holder.chipEstado.apply {
            text = if (curso.activo) "Activo" else "Inactivo"
            setChipBackgroundColorResource(
                if (curso.activo) R.color.success else R.color.danger
            )
            setTextColor(Color.WHITE)
        }
        holder.itemView.setOnClickListener {
            onClick(curso.idCurso)
        }
    }

    override fun getItemCount() = cursos.size

    fun actualizarLista(nuevaLista: List<Curso>) {
        cursos = nuevaLista
        notifyDataSetChanged()
    }
}

