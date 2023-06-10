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
import com.happytek.mercaplus.adapter.LinesAdapter
import com.happytek.mercaplus.adapter.SaleAdapter
import com.happytek.mercaplus.databinding.ActivityClienteDetalleBinding
import com.happytek.mercaplus.databinding.ActivityClientesBinding
import com.happytek.mercaplus.interfaces.RecyclerViewClickListener
import com.happytek.mercaplus.model.Cliente
import com.happytek.mercaplus.model.ClienteLista
import com.happytek.mercaplus.model.ListaProductos
import com.happytek.mercaplus.model.Sale
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import com.happytek.mercaplus.util.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteDetalleActivity : AppCompatActivity() , RecyclerViewClickListener {

     var id :Int = -1

    private lateinit var api: Services
    lateinit var binding: ActivityClienteDetalleBinding
    var sales : ArrayList<Sale> = arrayListOf()
    var clientes = ArrayList<ClienteLista>()
    var name : String = ""
    private lateinit var resultLauncherHistorial: ActivityResultLauncher<Intent>
    private lateinit var resultLauncherDatosCliente: ActivityResultLauncher<Intent>

     var cliente : Cliente? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(Constantes.EDAD, -1)

         api = Api.get()!!.create(Services::class.java)

        loadData()
        loadLayout()

        resultLauncherHistorial = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                loadHistorial(id);
                loadData()
            }
        }

        resultLauncherDatosCliente = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                loadData()
            }
        }
    }

    fun loadData() {

        var callClientes = api.getCliente(id);


        callClientes.enqueue(object : Callback<Cliente> {
            override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                binding.txtnombreComercial.text = response.body()!!.name
                 name = response.body()!!.name!!
                binding.txtnombreFiscal.text = response.body()!!.fiscal
                binding.txtdireccion.text = response.body()!!.address
                binding.txtlocalidad.text = response.body()!!.town!!.name + " (" + response.body()!!.town!!.province!!.name + ")"
                cliente = Cliente(response.body()!!.id, response.body()!!.name, response.body()!!.phone, response.body()!!.town, response.body()!!.fiscal, response.body()!!.address, response.body()!!.cp,response.body()!!.cif )
                loadHistorial(id)
            }

            override fun onFailure(call: Call<Cliente>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })

    }

    fun loadHistorial(id : Int) {
        var call = api.getSalesByClient(id)
        call.enqueue(object : Callback<List<Sale>> {
            override fun onResponse(call: Call<List<Sale>>, response: Response<List<Sale>>) {
                sales  = arrayListOf()
                for(res in response.body()!!) {
                    sales.add(Sale(res.id , res.createAt, res.totalAmount, res.mainAmount, res.taxAmount, res.idClient, name, res.codInvoice, res.paid))
                }
                binding.recyclerView.adapter = SaleAdapter(sales, this@ClienteDetalleActivity as RecyclerViewClickListener)
            }
            override fun onFailure(call: Call<List<Sale>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }


    override fun recyclerViewListClicked(value: Int) {
        TODO("Not yet implemented")
    }

    override fun getIntent(value: Intent) {
        resultLauncherHistorial.launch(value)
    }

    fun loadLayout() {
        binding.imgEditClient.setOnClickListener() {

            var intent = Intent(this, UpdateClienteActivity::class.java)
            intent.putExtra(Constantes.EDAD, cliente)
            resultLauncherDatosCliente.launch(intent)

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = null

        binding.btnServir.setOnClickListener() {
            var intent = Intent(this, ServirActivity::class.java)
            intent.putExtra(Constantes.EDAD, id)
            resultLauncherHistorial.launch(intent)

        }

    }
}


