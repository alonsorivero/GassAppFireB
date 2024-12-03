package com.example.gassappfireb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gassappfireb.databinding.ActivityPostLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostLogin : AppCompatActivity() {

    // Configurar ViewBinding para acceder a las vistas sin usar findViewById
    private lateinit var binding: ActivityPostLoginBinding
    // Configurar Firebase para la autenticación
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilitar el modo Edge-to-Edge para integrar mejor el contenido con los bordes del sistema
        enableEdgeToEdge()

        // Inicializar ViewBinding para vincular las vistas con el archivo XML correspondiente
        binding = ActivityPostLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar los márgenes para que las vistas se ajusten correctamente a los insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Encontrar el botón "menu" definido en el diseño y configurar un listener para cambiar de pantalla
        val cambiarPantalla: Button = findViewById(R.id.menu)
        cambiarPantalla.setOnClickListener {
            // Crear un Intent para abrir la actividad "Menu"
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Configurar el botón "Logout" para cerrar sesión
        binding.btnLogout.setOnClickListener {
            // Mostrar un diálogo de confirmación antes de cerrar sesión
            MaterialAlertDialogBuilder(this)
                .setTitle("Cerrar sesión") // Título del diálogo
                .setMessage("¿Estas seguro que deseas cerrar sesión?") // Mensaje del diálogo
                .setNeutralButton("Cancelar") { dialog, which ->
                    // No se realiza ninguna acción al presionar "Cancelar"
                }
                .setPositiveButton("Aceptar") { dialog, which ->
                    signOut() // Llamar a la función para cerrar sesión
                }
                .show() // Mostrar el diálogo
        }
    }

    // Función para cerrar sesión del usuario
    private fun signOut() {
        // Cerrar sesión de Firebase
        Firebase.auth.signOut()
        // Mostrar un mensaje de confirmación
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        // Finalizar la actividad actual
        finish()
    }
}




