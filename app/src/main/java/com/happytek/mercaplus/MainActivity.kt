package com.happytek.mercaplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.happytek.mercaplus.databinding.ActivityMainBinding
import com.happytek.mercaplus.util.Constantes

class MainActivity : AppCompatActivity() {

    var edad : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClientes.setOnClickListener{

                var intent = Intent(this, ClientesActivity::class.java)

                startActivity(intent)

        }

        binding.btnProductos.setOnClickListener{

            var intent = Intent(this, ProductosActivity::class.java)

            startActivity(intent)

        }

        binding.btnFacturas.setOnClickListener{

            var intent = Intent(this, FacturasActivity::class.java)

            startActivity(intent)

        }







    }
}