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
import com.happytek.mercaplus.databinding.ActivityClientesBinding
import com.happytek.mercaplus.databinding.ActivityServirProductosBinding
import com.happytek.mercaplus.model.Cliente
import com.happytek.mercaplus.model.ClienteLista
import com.happytek.mercaplus.model.Producto
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientesActivity : AppCompatActivity() {

    var clientes = ArrayList<ClienteLista>()

    private lateinit var api: Services
    lateinit var binding: ActivityClientesBinding

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
         binding.recyclerView.adapter = null
        api = Api.get()!!.create(Services::class.java)

        loadData()
        loadLayout()


        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                loadData()
            }
        }

    }

    fun loadData() {
        var callClientes = api.getClientes()
        callClientes.enqueue(object : Callback<List<ClienteLista>> {
            override fun onResponse(call: Call<List<ClienteLista>>, response: Response<List<ClienteLista>>) {
                clientes = arrayListOf()
                for(res in response.body()!!) {
                    clientes.add(ClienteLista(res.id , res.name, res.town))
                }
                binding.recyclerView.adapter = ClienteAdapter(clientes)
            }
            override fun onFailure(call: Call<List<ClienteLista>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }


    fun loadDataByName() {
        var callClientes = api.getClientesByName(binding.edtBuscarClientes.text.toString())
        clientes = ArrayList<ClienteLista>()
        callClientes.enqueue(object : Callback<List<ClienteLista>> {
            override fun onResponse(call: Call<List<ClienteLista>>, response: Response<List<ClienteLista>>) {
                for(res in response.body()!!) {
                    clientes.add(ClienteLista(res.id , res.name, res.town))
                }
                binding.recyclerView.adapter = ClienteAdapter(clientes)
            }
            override fun onFailure(call: Call<List<ClienteLista>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }

    fun loadLayout() {

        binding.btnBuscar.setOnClickListener() {

            loadDataByName()
        }

        binding.btnAdd.setOnClickListener{

            var intent = Intent(this, AddClienteActivity::class.java)

            resultLauncher.launch(intent)

        }
    }


}