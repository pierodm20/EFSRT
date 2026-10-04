package com.example.gestioncursos.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestioncursos.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (!validar(email, password)) return@setOnClickListener
            login(email, password)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if (currentUser != null){
            obtenerRol(currentUser.uid)
        }
    }

    private fun login(email: String, contrasena: String){
        auth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val uid = auth.currentUser?.uid?: return@addOnCompleteListener
                    obtenerRol(uid)
                }else{
                    mostrarMensaje("Credenciales incorrectas")
                }
            }
    }

    private fun obtenerRol(uid: String){
        db.collection("usuario").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()){
                    val email = document.getString("email") ?: auth.currentUser?.email
                    val rol = document.getString("rol")
                    val nombre = document.getString("nombre")
                    val apellido = document.getString("apellido")
                    val nombreCompleto = "${nombre} ${apellido}"
                    guardarUsuarioEnSesion(uid, email, nombreCompleto, rol)
                    redirigirPorRol(rol)
                }else{
                    mostrarMensaje("No existe usuario en la base de datos")
                }
            }
            .addOnFailureListener { exception ->
                mostrarMensaje("No se encuentra el rol: ${exception.message} ")
            }
    }

    private fun redirigirPorRol(rol: String?){
        val intent = when(rol){
            "ROL_ADMINISTRADOR" -> Intent(this, AdminDashboardActivity:: class.java)
            "ROL_PROFESOR" -> Intent(this, ProfesorDashboardActivity:: class.java)
            else -> Intent(this, AlumnoDashboardActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    fun guardarUsuarioEnSesion(uid: String?, email: String?, nombreCompleto: String?, rol: String?){
        val share = getSharedPreferences("Sesion_usuario", Context.MODE_PRIVATE)
        with(share.edit()) {
            putString("uid", uid)
            putString("email", email)
            putString("nombre", nombreCompleto)
            putString("rol", rol)
            apply()
        }

    }

    private fun validar(correo: String, contrasena: String): Boolean{
        if (correo.isEmpty()) {
            etEmail.error = "Ingrese su correo"
            etEmail.requestFocus()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etEmail.error = "Correo inválido"
            etEmail.requestFocus()
            return false
        }

        if (contrasena.isEmpty()) {
            etPassword.error = "Ingrese su contraseña"
            etPassword.requestFocus()
            return false
        }

        if (contrasena.length < 4) {
            etPassword.error = "Mínimo 4 caracteres"
            etPassword.requestFocus()
            return false
        }

        return true
    }

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}