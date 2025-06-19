package com.example.app_s9

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextUsername: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLoad: Button
    private lateinit var buttonClear: Button
    private lateinit var buttonClearCont: Button
    private lateinit var buttonIrPerfil: Button
    private lateinit var textViewResult: TextView
    private lateinit var textViewContador: TextView
    private lateinit var modoOscuro: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Inicializar SharedPreferencesHelper
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        
        // Inicializar vistas
        initViews()
        
        // Configurar listeners
        setupListeners()
        
        // Verificar si es la primera vez que se abre la app
        checkFirstTime()

        //Contador de visitas
        if (savedInstanceState == null) {
            // Solo contar si no es un reinicio
            contadorDeVisitas()
        }

        showContador()

        //Modo Oscuro
        modeDark()
    }
    
    private fun initViews() {
        editTextUsername = findViewById(R.id.editTextUsername)
        buttonSave = findViewById(R.id.buttonSave)
        buttonLoad = findViewById(R.id.buttonLoad)
        buttonClear = findViewById(R.id.buttonClear)
        buttonClearCont = findViewById(R.id.buttonClearCont)
        buttonIrPerfil = findViewById(R.id.buttonIrPerfil)
        textViewResult = findViewById(R.id.textViewResult)
        textViewContador = findViewById(R.id.textViewContador)
        modoOscuro = findViewById(R.id.modoOscuro)
    }
    
    private fun setupListeners() {
        buttonSave.setOnClickListener {
            saveData()
        }
        
        buttonLoad.setOnClickListener {
            loadData()
        }
        
        buttonClear.setOnClickListener {
            clearAllData()
        }

        buttonClearCont.setOnClickListener {
            resetCont()
        }

        buttonIrPerfil.setOnClickListener {
            val intent = Intent(this, PerfilUserActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun saveData() {
        val username = editTextUsername.text.toString().trim()
        
        if (username.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa un nombre", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Guardar datos
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_USERNAME, username)
        sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, false)
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_USER_ID, (1000..9999).random())
        
        Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
        editTextUsername.setText("")
    }
    
    private fun loadData() {
        val username = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_USERNAME, "Sin nombre")
        val isFirstTime = sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, true)
        val userId = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_USER_ID, 0)
        
        val result = "Usuario: $username\nID: $userId\nPrimera vez: ${if (isFirstTime) "Sí" else "No"}"
        textViewResult.text = result
    }
    
    private fun clearAllData() {
        sharedPreferencesHelper.clearAll()
        textViewResult.text = ""
        editTextUsername.setText("")
        Toast.makeText(this, "Todas las preferencias han sido eliminadas", Toast.LENGTH_SHORT).show()
    }
    
    private fun checkFirstTime() {
        val isFirstTime = sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, true)
        
        if (isFirstTime) {
            Toast.makeText(this, "¡Bienvenido por primera vez!", Toast.LENGTH_LONG).show()
        }
    }

    private fun contadorDeVisitas(){
        var cont = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_CONT_VISITS, 0)
        cont = cont + 1
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_CONT_VISITS, cont)
        textViewContador.text = "Visitas a la app: ${cont.toString()}"
    }

    private fun resetCont(){
        var cont = 0
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_CONT_VISITS, cont)
        textViewContador.text = "Visitas a la app: ${cont.toString()}"
    }

    private fun showContador(){
        var cont = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_CONT_VISITS, 0)
        sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_CONT_VISITS, cont)
        textViewContador.text = "Visitas a la app: ${cont.toString()}"
    }

    private fun modeDark(){
        // Evento de cambio
        modoOscuro.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Activado
                Toast.makeText(this, "Modo oscuro activado", Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Desactivado
                Toast.makeText(this, "Modo oscuro desactivado", Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}