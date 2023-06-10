package com.happytek.mercaplus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happytek.mercaplus.ClienteDetalleActivity
import com.happytek.mercaplus.R
import com.happytek.mercaplus.model.ClienteLista
import com.happytek.mercaplus.util.Constantes

class ClienteAdapter (val lista : ArrayList<ClienteLista>) : RecyclerView.Adapter<ClienteAdapter.MuroViewHolder>() {


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


        fun bindCliente(cliente: ClienteLista) {
            var txt1: TextView = itemView.findViewById(R.id.txt1) as TextView
            var txt2: TextView = itemView.findViewById(R.id.txt2) as TextView

            txt1.setText(cliente.name)
            txt2.setText(cliente.town)

            itemView.setOnClickListener() {
                var intent = Intent(itemView.context, ClienteDetalleActivity::class.java)
                intent.putExtra(Constantes.EDAD, cliente.id)
                itemView.context.startActivity(intent)

            }

        }
    }

     override fun getItemViewType(position: Int): Int {
          return super.getItemViewType(position)
      }



}