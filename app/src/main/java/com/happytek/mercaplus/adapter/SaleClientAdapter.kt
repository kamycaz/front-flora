package com.happytek.mercaplus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happytek.mercaplus.R
import com.happytek.mercaplus.SaleActivity
import com.happytek.mercaplus.model.Sale
import com.happytek.mercaplus.util.Constantes

class SaleClientAdapter (val lista : ArrayList<Sale>) : RecyclerView.Adapter<SaleClientAdapter.MuroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuroViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_sales_client, parent, false)

        return MuroViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MuroViewHolder, position: Int) {
        val itemProducto = lista[position]
        holder.bindCliente(itemProducto)
    }

    class MuroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindCliente(sale: Sale) {
            var txt1: TextView = itemView.findViewById(R.id.txtFecha) as TextView
            var txt2: TextView = itemView.findViewById(R.id.txtImporte) as TextView
            var txt3 : TextView = itemView.findViewById(R.id.txtCodFac) as TextView
            var txt4: TextView = itemView.findViewById(R.id.txtNombreClienteSaleList) as TextView
            var img1: ImageView = itemView.findViewById(R.id.imgSale) as ImageView

            txt1.setText(sale.createAt)
            txt2.setText(sale.totalAmount.toString() + " €")
            txt3.setText(sale.codInvoice)
            txt4.setText(sale.name)

            if(sale.paid == true) {
                img1.setImageResource(R.drawable.ic_facturapagada)
            } else {
                img1.setImageResource(R.drawable.ic_facturano)

            }
            itemView.setOnClickListener() {
                var intent = Intent(itemView.context, SaleActivity::class.java)
                intent.putExtra(Constantes.EDAD, sale)
                itemView.context.startActivity(intent)

            }
        }
    }



}