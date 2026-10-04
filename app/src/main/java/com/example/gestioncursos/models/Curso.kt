package com.example.gestioncursos.models

data class Curso(
    val idCurso: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val idProfesor: String = "",
    val horario: String = "",
    val alumnos: List<String> = emptyList(),
    val activo: Boolean = true,
)
