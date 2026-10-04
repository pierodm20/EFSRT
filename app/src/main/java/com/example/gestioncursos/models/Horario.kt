package com.example.gestioncursos.models

import com.example.gestioncursos.enums.Turno
import java.time.LocalTime
import java.time.format.DateTimeParseException

data class Horario(
    val turno: Turno = Turno.Manana,
    val dias: String = "",
    val horaInicio: String = "00:00",
    val horaFin: String = "00:00",
    val aula: String = ""
){
    fun getLocalTimeInicio(): LocalTime {
        return try {
            LocalTime.parse(horaInicio)
        } catch (e: Exception) {
            LocalTime.MIN // Retorna 00:00 si la cadena no es válida
        }
    }

    fun getLocalTimeFin(): LocalTime {
        return try {
            LocalTime.parse(horaFin)
        } catch (e: Exception) {
            LocalTime.MAX // Retorna 23:59:59 si la cadena no es válida
        }
    }
}