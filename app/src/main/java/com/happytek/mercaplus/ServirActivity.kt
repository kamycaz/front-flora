package com.happytek.mercaplus

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.happytek.mercaplus.adapter.LinesAdapter
import com.happytek.mercaplus.databinding.ActivitySaleBinding
import com.happytek.mercaplus.databinding.ActivityServirBinding
import com.happytek.mercaplus.model.*
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import com.happytek.mercaplus.util.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class ServirActivity : AppCompatActivity() {

    var id :Int = -1

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    var productosAñadidos = ArrayList<Producto>()

    private lateinit var context : Context
    lateinit var binding: ActivityServirBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityServirBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recylerLines.layoutManager = LinearLayoutManager(this)
        context = this

        id = intent.getIntExtra(Constantes.EDAD, -1)

        loadLayout()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val list : ListaProductos? = data!!.getParcelableExtra("productos")
                productosAñadidos.addAll(list!!.list)
                for( producto in productosAñadidos) {
                    Log.d("TAG Lista", "Añadido: " + producto!!.name + " X " + producto.cantidad)
                }
                binding.recylerLines.adapter = LinesAdapter(productosAñadidos)

            }
        }
    }

    fun mapper ( list : ArrayList<Producto> ) : SaleForm  {

        val lines : ArrayList<ProductLine> = arrayListOf()
        for (product in list) {
            lines.add(ProductLine(product.id!!, product.cantidad, product.prize, product.iva, 0, product.prize * BigDecimal(product.cantidad)))
        }


        var saleForm : SaleForm = SaleForm(id, lines)

        return saleForm


    }

    fun loadLayout() {
        binding.btnAddProducto.setOnClickListener() {

            var intent = Intent(this, ServirProductosActivity::class.java)

            resultLauncher.launch(intent)
        }

        binding.btnFacturar.setOnClickListener() {
            var api = Api.get()!!.create(Services::class.java)
            var call = api.addProduct(mapper(productosAñadidos))
            call.enqueue(object : Callback<StandarResponse> {
                override fun onResponse(call: Call<StandarResponse>, response: Response<StandarResponse>) {

                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_LONG).show()
                    if (response.body()!!.code.equals("OK")){
                        val intent = Intent()
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                }
                override fun onFailure(call: Call<StandarResponse>, t: Throwable) {
                    Log.e("TAG Error" , t.toString())
                }
            })


        }



    }

}