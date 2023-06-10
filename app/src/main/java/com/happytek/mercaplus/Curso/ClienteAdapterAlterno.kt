package com.happytek.mercaplus.Curso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happytek.mercaplus.model.ClienteLista
import com.happytek.mercaplus.R

class ClienteAdapterAlterno(val lista : ArrayList<ClienteLista>) : RecyclerView.Adapter<ClienteAdapterAlterno.MuroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuroViewHolder {
        //Este seria el metodo basico, lo de abajo es para alternar colores entre filas
        var layoutInflater = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_basic, parent, false)

    /*   if(getItemViewType()%2==0) {
           layoutInflater = LayoutInflater.from(parent.context)
               .inflate(R.layout.list_item_clientes, parent, false)
       } else {
           layoutInflater = LayoutInflater.from(parent.context)
               .inflate(R.layout.list_item_clientes_primero, parent, false)
       } */

        when(viewType) {
            0 -> layoutInflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_basic, parent, false)
            1 -> layoutInflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_clientes_primero, parent, false)
        }

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
     /*       var txt1: TextView = itemView.findViewById(R.id.txt1) as TextView
            var txt2: TextView = itemView.findViewById(R.id.txt2) as TextView
            var img1: ImageView = itemView.findViewById(R.id.img1) as ImageView

            img1.setImageResource(cliente.foto)
            txt1.setText(cliente.nombreUser)
            txt2.setText(cliente.localidad)

            itemView.setOnClickListener() {
                var intent = Intent(itemView.context, ClienteDetalleActivity::class.java)
                intent.putExtra(Constantes.EDAD, cliente.nombreUser)
                itemView.context.startActivity(intent)

            } */

        }
    }

  /*  override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    } */

   override fun getItemViewType(position: Int): Int {
        var viewType : Int = 1
        if(position%2==0) {
            viewType = 0
        } else {
            viewType = 1
        }

        return viewType
    }

}