package com.example.gestioncursos.enums

import com.google.firebase.database.PropertyName

enum class Turno {
    @PropertyName("Mañana")
    Manana,
    Tarde,
    Noche
}