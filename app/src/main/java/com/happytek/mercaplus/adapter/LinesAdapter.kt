package com.happytek.mercaplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happytek.mercaplus.R
import com.happytek.mercaplus.model.Producto
import java.math.BigDecimal

class LinesAdapter (val lista : ArrayList<Producto>) : RecyclerView.Adapter<LinesAdapter.MuroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuroViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_basic, parent, false)

        return MuroViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MuroViewHolder, position: Int) {
        val itemCliente = lista[position]
        holder.bindCliente(itemCliente)
    }

    class MuroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindCliente(producto: Producto) {
            var txt1: TextView = itemView.findViewById(R.id.txt1) as TextView
            var txt2: TextView = itemView.findViewById(R.id.txt2) as TextView

            txt1.setText(producto.cantidad.toString() + " X " + producto.name)
            txt2.setText((producto.prize * BigDecimal(producto.cantidad)).toString())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }



}