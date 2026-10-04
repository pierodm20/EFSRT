package com.example.gestioncursos.models

data class Tarea(
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val curso: String,
    val fechaLimite: String,
    val estado: String
)