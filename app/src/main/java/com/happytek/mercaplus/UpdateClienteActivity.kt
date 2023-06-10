package com.happytek.mercaplus

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.happytek.mercaplus.databinding.ActivityUpdateClienteBinding
import com.happytek.mercaplus.model.*
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import com.happytek.mercaplus.util.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateClienteActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateClienteBinding
    private lateinit var api: Services
    var regionString : ArrayList<String> = arrayListOf()
    var regions: ArrayList<Region> = arrayListOf()
    var provinceString : ArrayList<String> = arrayListOf()
    var provinces: ArrayList<Province> = arrayListOf()
    var townString : ArrayList<String> = arrayListOf()
    var towns: ArrayList<Town> = arrayListOf()
    var cp : String = "-1"
    var idTown : Int = -1
     var cliente : Cliente? = null
    var cargaInicial : Int = 0

    private lateinit var context : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api = Api.get()!!.create(Services::class.java)
        context = this

        cliente = intent.getParcelableExtra(Constantes.EDAD)
        loadClientData()
        loadRegionData()
        loadLayout()

    }

    fun loadRegionData() {
        var callClientes = api.getRegions()
        callClientes.enqueue(object : Callback<List<Region>> {
            override fun onResponse(call: Call<List<Region>>, response: Response<List<Region>>) {
                regionString  = arrayListOf()
                regions = arrayListOf()
                regionString.add("")
                regions.add(Region(-1,""))
                for(res in response.body()!!) {
                    regions.add(res)
                    regionString.add(res.name)
                }
                loadRegionSpinner(regionString)
               if(cargaInicial == 0)
                    loadClientRegion()
            }

            override fun onFailure(call: Call<List<Region>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }

    fun loadProvinceData(id : Int) {
        var callClientes = api.getProvinciasByRegion(id)
        callClientes.enqueue(object : Callback<List<Province>> {
            override fun onResponse(call: Call<List<Province>>, response: Response<List<Province>>) {
                provinceString = arrayListOf()
                provinces = arrayListOf()
                provinceString.add("")
                provinces.add(Province(-1,"",null))
                for(res in response.body()!!) {
                    provinces.add(res)
                    provinceString.add(res.name)
                }
                loadProvinceSpinner(provinceString)
                if(cargaInicial == 0)
                    loadClientProvince()
            }
            override fun onFailure(call: Call<List<Province>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }

    fun loadTownData(id : Int) {
        var callClientes = api.getTownByProvince(id)
        callClientes.enqueue(object : Callback<List<Town>> {
            override fun onResponse(call: Call<List<Town>>, response: Response<List<Town>>) {
                townString = arrayListOf()
                towns = arrayListOf()
                townString.add("")
                towns.add(Town(-1,"", "-1",null))
                for(res in response.body()!!) {
                    towns.add(res)
                    townString.add(res.name + " (" + res.cp + ")")
                }
                loadTownSpinner(townString)
                cp = towns.get(0).cp!!

                if(cargaInicial == 0) {
                    loadClientTown()
                    cargaInicial = 1
                }

                Log.e("TAG CP" , cp.toString())
            }
            override fun onFailure(call: Call<List<Town>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }

    fun loadRegionSpinner (list : ArrayList<String>) {

        binding.spnRegion.adapter = ArrayAdapter( this, R.layout.simple_list_item_1, list)

    }

    fun loadProvinceSpinner (list : ArrayList<String>) {

        binding.spnProvincia.adapter = ArrayAdapter( this, R.layout.simple_list_item_1, list)

    }

    fun loadTownSpinner (list : ArrayList<String>) {

        binding.spnLocalidad.adapter = ArrayAdapter( this, R.layout.simple_list_item_1, list)

    }

    fun loadClientData() {

        binding.edtNombreComercial.setText(cliente!!.name)
        binding.edtNombreFiscal.setText(cliente!!.fiscal)
        binding.edtCIF.setText(cliente!!.cif)
        binding.edtDireccion.setText(cliente!!.address)
        binding.edtTelefono.setText(cliente!!.phone)

    }

    fun loadClientRegion() {

        var contador : Int = 0
        for (region in regions) {
            if(cliente!!.town!!.province!!.region!!.id == region.id) {
                binding.spnRegion.setSelection(contador)
            } else {
                contador++
            }
        }

    }

    fun loadClientProvince() {

        var contador : Int = 0
        for (province in provinces) {
            if(cliente!!.town!!.province!!.id == province.id) {
                binding.spnProvincia.setSelection(contador)
            } else {
                contador++
            }
        }

    }

    fun loadClientTown() {

        var contador : Int = 0
        for (town in towns) {
            if(cliente!!.town!!.id == town.id) {
                binding.spnLocalidad.setSelection(contador)
            } else {
                contador++
            }
        }

    }

    fun loadLayout() {

        binding.spnRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Log.e("TAG Selected:" , regions.get(position).name)
                loadProvinceData(regions.get(position).id)

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        binding.spnProvincia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Log.e("TAG Selected:" , provinces.get(position).name)
                loadTownData(provinces.get(position).id)

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        binding.spnLocalidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Log.e("TAG Selected:" , towns.get(position).name!!)
                cp = towns.get(position).cp!!
                idTown = towns.get(position).id!!
                Log.e("TAG CP" , cp.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        binding.btnAddCliente.setOnClickListener() {

            var name : String = binding.edtNombreComercial.text.toString()
            var fiscal : String = binding.edtNombreFiscal.text.toString()
            var phone : String = binding.edtTelefono.text.toString()
            var cp : String = cp.toString()
            var address : String = binding.edtDireccion.text.toString()
            var town : Town = Town(idTown, null, null, null)
            var cif : String = binding.edtCIF.text.toString()

            var cliente : Cliente = Cliente(cliente!!.id,name, phone, town, fiscal, address, cp, cif)

            var call = api.addClient(cliente)
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