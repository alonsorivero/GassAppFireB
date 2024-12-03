package com.example.gassappfireb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gassappfireb.databinding.ActivityRegistrarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrarActivity : AppCompatActivity() {

    // Configurar ViewBinding para acceder a las vistas en el diseño
    private lateinit var binding: ActivityRegistrarBinding
    // Configurar Firebase para autenticación
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilitar Edge-to-Edge para que el diseño ocupe toda la pantalla
        enableEdgeToEdge()

        // Inicializar ViewBinding
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajustar márgenes para los insets del sistema (barras de estado y navegación)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Configurar el listener para el botón de registro
        binding.btnRegistrar.setOnClickListener {
            // Obtener el correo y las contraseñas ingresadas por el usuario
            val email = binding.etEmail.text.toString()
            val pass1 = binding.etPassword.text.toString()
            val pass2 = binding.etPassword2.text.toString()

            // Validar que el correo no esté vacío
            if (email.isEmpty()) {
                binding.etEmail.error = "Por favor ingrese un correo"
                return@setOnClickListener
            }

            // Validar que la primera contraseña no esté vacía
            if (pass1.isEmpty()) {
                binding.etPassword.error = "Por favor ingrese una contraseña"
                return@setOnClickListener
            }

            // Validar que la segunda contraseña no esté vacía
            if (pass2.isEmpty()) {
                binding.etPassword2.error = "Por favor ingrese la contraseña nuevamente"
                return@setOnClickListener
            }

            // Validar que ambas contraseñas coincidan
            if (pass1 != pass2) {
                binding.etPassword2.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            } else {
                // Si todo está correcto, proceder con el registro
                signUp(email, pass1)
            }
        }
    }

    /**
     * Método para registrar un nuevo usuario en Firebase Authentication.
     * @param email El correo electrónico del usuario.
     * @param pass1 La contraseña del usuario.
     */
    private fun signUp(email: String, pass1: String) {
        // Crear un usuario en Firebase Authentication
        auth.createUserWithEmailAndPassword(email, pass1)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Mostrar un mensaje indicando que el registro fue exitoso
                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    // Navegar a la actividad principal
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Mostrar un mensaje de error si el registro falló
                    Toast.makeText(this, "Error en el registro del usuario", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

