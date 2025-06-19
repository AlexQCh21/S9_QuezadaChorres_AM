package com.example.app_s9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerfilUserActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextNombre: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonCargar: Button
    private lateinit var buttonGuardarPerfil: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Iniciamos los componente
        initViews()

        //Configuramos los eventos de los botones
        setupListeners()

    }

//    const val KEY_NAME = "name"
//    const val KEY_AGE = "age"
//    const val KEY_EMAIL = "email"

    private fun initViews() {
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextEdad = findViewById(R.id.editTextEdad)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonCargar = findViewById(R.id.buttonCargar)
        buttonGuardarPerfil = findViewById(R.id.buttonGuardarPerfil)
    }

    private fun setupListeners() {
        buttonCargar.setOnClickListener {

            loadData()
        }

        buttonGuardarPerfil.setOnClickListener {
            saveData()
        }

    }

    private fun saveData() {
        val name = editTextNombre.text.toString().trim()
        val edad = editTextEdad.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (name.isEmpty() && edad.isEmpty() && email.isEmpty()) {
            Toast.makeText(this, "Por favor completar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar datos
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_NAME, name)
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_AGE, edad.toInt())
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_EMAIL, email)

        Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
        editTextNombre.setText("")
        editTextEdad.setText("")
        editTextEmail.setText("")
    }

    private fun loadData() {
        val name = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_NAME, "Sin nombre")
        val edad = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_AGE, 18)
        val email = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_EMAIL, "sin email")

        editTextNombre.setText(name)
        editTextEdad.setText(edad.toString())
        editTextEmail.setText(email)
    }
}