package com.example.gassappfireb

// Importaciones necesarias para la funcionalidad
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gassappfireb.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // Configurar ViewBinding para acceder a las vistas definidas en el layout
    private lateinit var binding: ActivityMainBinding

    // Instancia de Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilitar Edge-to-Edge para ocupar toda la pantalla, incluidas las barras de estado y navegación
        enableEdgeToEdge()

        // Inicializar ViewBinding para vincular la vista de la actividad
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajustar márgenes del diseño para las barras del sistema (estado y navegación)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firebase Authentication
        auth = Firebase.auth

        // Configurar el botón de login
        binding.btnLogin.setOnClickListener {
            // Obtener el correo y contraseña desde los campos de texto
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Validar que el correo no esté vacío
            if (email.isEmpty()) {
                binding.etEmail.error = "Por favor ingrese un correo"
                return@setOnClickListener
            }

            // Validar que la contraseña no esté vacía
            if (password.isEmpty()) {
                binding.etPassword.error = "Por favor ingrese la contraseña"
                return@setOnClickListener
            }

            // Iniciar sesión con Firebase
            signIn(email, password)
        }

        // Configurar el enlace para ir a la pantalla de registro
        binding.tvRegistrar.setOnClickListener {
            try {
                // Crear intent para navegar hacia `RegistrarActivity`
                val intent = Intent(this, RegistrarActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                // Mostrar un mensaje en caso de error
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para iniciar sesión en Firebase
    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Inicio de sesión exitoso, mostrar mensaje al usuario
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()

                    // Ir a la actividad de PostLogin
                    try {
                        val intent = Intent(this, PostLogin::class.java)
                        startActivity(intent)
                    } catch (e: Exception) {
                        // Mostrar mensaje en caso de error al iniciar la nueva actividad
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Inicio de sesión fallido, mostrar mensaje al usuario
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

