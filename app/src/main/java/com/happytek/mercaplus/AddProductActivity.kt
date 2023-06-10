package com.happytek.mercaplus

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.happytek.mercaplus.databinding.ActivityAddClienteBinding
import com.happytek.mercaplus.databinding.ActivityAddProductBinding
import com.happytek.mercaplus.model.Cliente
import com.happytek.mercaplus.model.Producto
import com.happytek.mercaplus.model.StandarResponse
import com.happytek.mercaplus.model.Town
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class AddProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddProductBinding
    private lateinit var api: Services
    private lateinit var context : Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        api = Api.get()!!.create(Services::class.java)
        context = this

        binding.btnAddProducto.setOnClickListener() {

            var name : String = binding.edtNombre.text.toString()
            var description : String = binding.edtDescripcion.text.toString()
            var prize : String = binding.edtPrecio.text.toString()
            var prizeDecimal : BigDecimal = BigDecimal(prize)
            var iva : Int = Integer.valueOf(binding.edtIva.text.toString())

            var producto : Producto = Producto(null, name, description, prizeDecimal,iva, 0)

            var call = api.addProduct(producto)
            call.enqueue(object : Callback<StandarResponse> {
                override fun onResponse(call: Call<StandarResponse>, response: Response<StandarResponse>) {
                    if(response.body()!!.code.equals("OK")) {
                        Log.d("TAG GUAY", "Resultado: " + response.body()!!.message)
                        Toast.makeText(context, response.body()!!.message, Toast.LENGTH_LONG).show()

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