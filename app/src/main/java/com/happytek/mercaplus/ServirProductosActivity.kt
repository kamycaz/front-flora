package com.happytek.mercaplus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.happytek.mercaplus.adapter.ProductoServirAdapter
import com.happytek.mercaplus.databinding.ActivityServirProductosBinding
import com.happytek.mercaplus.interfaces.RecyclerViewClickListener
import com.happytek.mercaplus.model.ListaProductos
import com.happytek.mercaplus.model.Producto
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServirProductosActivity : AppCompatActivity(), RecyclerViewClickListener   {

    var productos = ArrayList<Producto>()
    var productosAñadidos = ArrayList<Producto>()
    var position : Int = 0

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityServirProductosBinding

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityServirProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

            loadProductos()
                loadLayout()
         resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val producto :Producto?
                val cantidad : Int
                producto = data!!.getParcelableExtra("producto")
                productosAñadidos.add(producto!!)
               for( producto in productosAñadidos)
                Log.d("TAG Lista", "Añadido: " + producto!!.name + " X " + producto.cantidad)
            }
        }


    }

    override fun recyclerViewListClicked(value: Int) {
        position = value
        Log.d("TAG", position.toString())
    }

    override fun getIntent(value: Intent) {

        resultLauncher.launch(value)
    }

    fun loadProductos() {

        binding.recyclerProductos.layoutManager = LinearLayoutManager(this)
        binding.recyclerProductos.adapter = null

        var apiClientes = Api.get()!!.create(Services::class.java)
        var callClientes = apiClientes.getProducts()
        callClientes.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                for(res in response.body()!!) {
                    productos.add(Producto(res.id , res.name, res.description,res.prize, res.iva, 0))
                }
                binding.recyclerProductos.adapter = ProductoServirAdapter(productos, this@ServirProductosActivity as RecyclerViewClickListener)

            }
            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })


    }

    fun loadLayout() {

        binding.btnGuardar.setOnClickListener() {
            var listado : ListaProductos = ListaProductos(productosAñadidos)
            val intent = Intent()
            intent.putExtra("productos", listado)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }


}
