package com.happytek.mercaplus

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.happytek.mercaplus.R
import com.happytek.mercaplus.adapter.LinesDetailsAdapter
import com.happytek.mercaplus.adapter.SaleAdapter
import com.happytek.mercaplus.databinding.ActivityClienteDetalleBinding
import com.happytek.mercaplus.databinding.ActivitySaleBinding
import com.happytek.mercaplus.model.Sale
import com.happytek.mercaplus.model.SaleLine
import com.happytek.mercaplus.model.StandarResponse
import com.happytek.mercaplus.service.Api
import com.happytek.mercaplus.service.Services
import com.happytek.mercaplus.util.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class SaleActivity : AppCompatActivity() {

    var sale : Sale? = null

    lateinit var binding: ActivitySaleBinding
    private lateinit var api: Services
    var lines : ArrayList<SaleLine> = arrayListOf()

    private lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sale = intent.getParcelableExtra(Constantes.EDAD)

        api = Api.get()!!.create(Services::class.java)

        context = this


        binding.recylerLines.layoutManager = LinearLayoutManager(this)
        binding.recylerLines.adapter = null

        loadLayout()
        loadHeader()
        loadLines(sale!!.id)
    }

    fun loadHeader() {

        binding.txtNombreClienteSales.text = sale!!.name
        binding.txtFechaSale.text = sale!!.createAt
        binding.txtNumFac.text = sale!!.codInvoice

    }

    fun loadLines(id : Int) {
        var call = api.getSalesLinesBySale(id)
        call.enqueue(object : Callback<List<SaleLine>> {
            override fun onResponse(call: Call<List<SaleLine>>, response: Response<List<SaleLine>>) {
                lines  = arrayListOf()
                for(res in response.body()!!) {
                    lines.add(SaleLine(res.id , res.name, res.quantity, res.iva, res.prize, res.totalAmount))
                }
                binding.recylerLines.adapter = LinesDetailsAdapter(lines)
                loadLTotal()
            }
            override fun onFailure(call: Call<List<SaleLine>>, t: Throwable) {
                Log.e("TAG" , t.toString())
            }
        })
    }

    fun loadLTotal() {

        var subtotal : BigDecimal = BigDecimal.valueOf(0)
        var iva : BigDecimal = BigDecimal.ZERO
        var total : BigDecimal = BigDecimal.ZERO

        for ( line in lines ) {

           subtotal =  subtotal.add(line.prize.multiply(line.quantity.toBigDecimal()))

           iva =  iva.add(((line.prize.multiply(line.quantity.toBigDecimal())).multiply(line.iva.toBigDecimal())).divide(
                BigDecimal.valueOf(100)))
        }
       total =  total.add(subtotal).add(iva)
        binding.txtSubTotal.text ="Subtotal: " + subtotal.toString() + "€"
        binding.txtIva.text = "IVA: " +iva.toString() + "€"
        binding.txtTotal.text = "Total: " + subtotal.add(iva).toString() + "€"

    }

    fun setPaid() {
        binding.btnCobrar.isEnabled = false
        binding.txtPagado.text = "Pagado"
        binding.txtPagado.setTextColor(getColor(R.color.green))
    }

    fun setNoPaid() {
        binding.btnCobrar.isEnabled = true
        binding.txtPagado.text = "Pendiente de pago"
        binding.txtPagado.setTextColor(getColor(R.color.red))


    }

    fun loadLayout() {

        if(sale!!.paid == true) {
            setPaid()
        } else {
            setNoPaid()
        }

        binding.btnCobrar.setOnClickListener() {
            var call = api.setPaid(sale!!.id);
            call.enqueue(object : Callback<StandarResponse> {
                override fun onResponse(call: Call<StandarResponse>, response: Response<StandarResponse>) {
                    //  Log.d("TAG Añadido", "Añadido: " + response.body()!!.name)
                    Log.d("TAG GUAY", "Resultado: " + response.body()!!.message)

                    if (response.body()!!.code.equals("OK")) {
                        setPaid()
                        val intent = Intent()
                        setResult(Activity.RESULT_OK, intent)
                        Toast.makeText(context, response.body()!!.message, Toast.LENGTH_LONG).show()
                    }

                }
                override fun onFailure(call: Call<StandarResponse>, t: Throwable) {
                    Log.e("TAG Error" , t.toString())
                }
            })


        }
    }


}