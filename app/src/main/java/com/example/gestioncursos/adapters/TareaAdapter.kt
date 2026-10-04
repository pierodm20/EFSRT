package com.example.gestioncursos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestioncursos.R
import com.example.gestioncursos.models.Tarea

class TareaAdapter(
    private val lista: ArrayList<Tarea>,
    private val onCompletarClick: (Tarea) -> Unit,
    private val onEditarClick: (Tarea) -> Unit,
    private val onEliminarClick: (Tarea) -> Unit
) : RecyclerView.Adapter<TareaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTituloTarea)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcionTarea)
        val txtCurso: TextView = itemView.findViewById(R.id.txtCursoTarea)
        val txtFecha: TextView = itemView.findViewById(R.id.txtFechaLimiteTarea)
        val txtEstado: TextView = itemView.findViewById(R.id.txtEstadoTarea)
        val btnCompletar: Button = itemView.findViewById(R.id.btnCompletarTarea)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditarTarea)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarTarea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = lista[position]

        holder.txtTitulo.text = tarea.titulo
        holder.txtDescripcion.text = tarea.descripcion
        holder.txtCurso.text = "Curso: ${tarea.curso}"
        holder.txtFecha.text = "Fecha límite: ${tarea.fechaLimite}"
        holder.txtEstado.text = "Estado: ${tarea.estado}"

        holder.btnCompletar.setOnClickListener {
            onCompletarClick(tarea)
        }

        holder.btnEditar.setOnClickListener {
            onEditarClick(tarea)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(tarea)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}