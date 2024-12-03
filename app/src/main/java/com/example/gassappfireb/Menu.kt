package com.example.gassappfireb

// Importaciones necesarias para la actividad
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gassappfireb.databinding.ActivityMenuBinding
import com.example.gassappfireb.vistas.AcercaFragment
import com.example.gassappfireb.vistas.InicioFragment
import com.example.gassappfireb.vistas.PerfilFragment

class Menu : AppCompatActivity() {

    // Inicialización de ViewBinding para acceder a las vistas definidas en el layout
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilitar Edge-to-Edge para ocupar toda la pantalla (barras de estado y navegación incluidas)
        enableEdgeToEdge()

        // Configuración de ViewBinding
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajustar márgenes del diseño para las barras del sistema (estado y navegación)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Si no hay estado guardado (primer inicio de la actividad), cargar el fragmento inicial
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, InicioFragment()) // Cargar InicioFragment en el contenedor
                .commit()
        }

        // Configurar el listener del menú de navegación inferior
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_1 -> { // Opción 1 seleccionada
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, InicioFragment()) // Cambiar al InicioFragment
                        .commit()
                    true
                }
                R.id.item_2 -> { // Opción 2 seleccionada
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AcercaFragment()) // Cambiar al AcercaFragment
                        .commit()
                    true
                }
                R.id.item_3 -> { // Opción 3 seleccionada
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PerfilFragment()) // Cambiar al PerfilFragment
                        .commit()
                    true
                }
                else -> false // Si no coincide con ninguna opción
            }
        }
    }
}


