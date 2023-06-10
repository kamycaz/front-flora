package com.happytek.mercaplus

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.happytek.mercaplus.databinding.ActivityProductoDetalleBinding
import com.happytek.mercaplus.databinding.ActivityProductosBinding
import com.happytek.mercaplus.model.Cliente
import com.happytek.mercaplus.model.Producto
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import com.happytek.mercaplus.util.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoDetalleActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductoDetalleBinding
    private lateinit var api: Services
    var id :Int = -1
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    var producto : Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(Constantes.EDAD, -1)
        loadProduct()
        loadLayout()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
              loadProduct()
            }
        }

    }

    fun loadProduct() {

        var api = Api.get()!!.create(Services::class.java)
        var call = api.getProduct(id);
        call.enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                binding.txtNombre.text = response.body()!!.name
                binding.txtDescripcion.text = response.body()!!.description
                binding.txtPrecio.text = response.body()!!.prize.toString() + "€"
                binding.txtIva.text = response.body()!!.iva.toString() + "%"
                producto = Producto(id, response.body()!!.name, response.body()!!.description, response.body()!!.prize, response.body()!!.iva, 0)
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })


    }

    fun loadLayout() {
        binding.btnEditarProducto.setOnClickListener() {

            var intent = Intent(this, UpdateProductActivity::class.java)
            intent.putExtra(Constantes.EDAD, producto)
            resultLauncher.launch(intent)

        }

    }
}