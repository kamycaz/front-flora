package com.happytek.mercaplus

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.happytek.mercaplus.adapter.ClienteAdapter
import com.happytek.mercaplus.adapter.ProductoAdapter
import com.happytek.mercaplus.adapter.ProductoServirAdapter
import com.happytek.mercaplus.databinding.ActivityProductosBinding
import com.happytek.mercaplus.interfaces.RecyclerViewClickListener
import com.happytek.mercaplus.model.ClienteLista
import com.happytek.mercaplus.model.Producto
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductosActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductosBinding
    private lateinit var api: Services
    var productos = ArrayList<Producto>()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        api = Api.get()!!.create(Services::class.java)


        loadProductos()
        loadLayout()


        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                loadProductos()
            }
        }

    }

    fun loadProductos() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = null


        var callClientes = api.getProducts()
        callClientes.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                productos = arrayListOf()
                for(res in response.body()!!) {
                    productos.add(Producto(res.id , res.name, res.description,res.prize, res.iva, 0))
                }
                binding.recyclerView.adapter = ProductoAdapter(productos)

            }
            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })


    }

    fun loadDataByName() {
        var callClientes = api.getProductosByName(binding.edtBuscar.text.toString())
        productos = arrayListOf()
        callClientes.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                for(res in response.body()!!) {
                    productos.add(Producto(res.id , res.name, res.description,res.prize, res.iva, 0))
                }
                binding.recyclerView.adapter = ProductoAdapter(productos)
            }
            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }

    fun loadLayout() {

        binding.btnAdd.setOnClickListener{

            var intent = Intent(this, AddProductActivity::class.java)

            resultLauncher.launch(intent)

        }

        binding.btnBuscar.setOnClickListener() {

            loadDataByName()
        }

    }
}