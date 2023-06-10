package com.happytek.mercaplus

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.happytek.mercaplus.databinding.ActivityServirProductoCantidadBinding
import com.happytek.mercaplus.model.Producto
import com.happytek.mercaplus.util.Constantes

class ServirProductoCantidadActivity : Activity() {

    var producto : Producto? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityServirProductoCantidadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        producto = intent.getParcelableExtra(Constantes.EDAD)

        if(producto != null) {
            binding.txtNombreProducto.text = producto!!.name
        }

        binding.edtCantidad.requestFocusFromTouch()

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        binding.btnAAdir.setOnClickListener() {
            val intent = Intent()
            producto!!.cantidad = Integer.parseInt(binding.edtCantidad.text.toString())
            intent.putExtra("producto", producto)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.btnDescartar.setOnClickListener() {
            finish()
        }








    }
}