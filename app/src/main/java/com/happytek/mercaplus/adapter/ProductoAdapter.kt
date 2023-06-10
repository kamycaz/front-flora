package com.happytek.mercaplus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happytek.mercaplus.ProductoDetalleActivity
import com.happytek.mercaplus.R
import com.happytek.mercaplus.ServirProductoCantidadActivity
import com.happytek.mercaplus.interfaces.RecyclerViewClickListener
import com.happytek.mercaplus.model.Producto
import com.happytek.mercaplus.util.Constantes

class ProductoAdapter  (val lista : ArrayList<Producto>) : RecyclerView.Adapter<ProductoAdapter.MuroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuroViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_basic, parent, false)

        return MuroViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MuroViewHolder, position: Int) {
        val itemProducto = lista[position]
        holder.bindCliente(itemProducto)
        /*    holder.itemView.setOnClickListener(){
                onItemClickValue.recyclerViewListClicked(position)
            } */

        holder.itemView.setOnClickListener() {
            var intent = Intent(holder.itemView.context, ProductoDetalleActivity::class.java)
            intent.putExtra(Constantes.EDAD, itemProducto.id)

              holder.itemView.context.startActivity(intent)

        }
    }

    class MuroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindCliente(producto: Producto) {
            var txt1: TextView = itemView.findViewById(R.id.txt1) as TextView
            var txt2: TextView = itemView.findViewById(R.id.txt2) as TextView

            txt1.setText(producto.name)
            txt2.setText(producto.prize.toString())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}