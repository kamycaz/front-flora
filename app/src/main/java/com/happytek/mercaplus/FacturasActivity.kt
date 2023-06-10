package com.happytek.mercaplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.happytek.mercaplus.adapter.SaleAdapter
import com.happytek.mercaplus.adapter.SaleClientAdapter
import com.happytek.mercaplus.databinding.ActivityClientesBinding
import com.happytek.mercaplus.databinding.ActivityFacturasBinding
import com.happytek.mercaplus.model.ClienteLista
import com.happytek.mercaplus.model.Sale
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FacturasActivity : AppCompatActivity() {

    var sales = ArrayList<Sale>()

    private lateinit var api: Services
    lateinit var binding: ActivityFacturasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacturasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = null
        api = Api.get()!!.create(Services::class.java)

        loadData()
    }

    fun loadData() {
        var call = api.getSales();
        call.enqueue(object : Callback<List<Sale>> {
            override fun onResponse(call: Call<List<Sale>>, response: Response<List<Sale>>) {
                sales  = arrayListOf()
                for(res in response.body()!!) {
                    sales.add(Sale(res.id , res.createAt, res.totalAmount, res.mainAmount, res.taxAmount, res.idClient, res.name, res.codInvoice, res.paid))
                }
                binding.recyclerView.adapter = SaleClientAdapter(sales)
            }
            override fun onFailure(call: Call<List<Sale>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }
}