package com.happytek.mercaplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happytek.mercaplus.R
import com.happytek.mercaplus.model.Producto
import com.happytek.mercaplus.model.SaleLine
import java.math.BigDecimal

class LinesDetailsAdapter (val lista : ArrayList<SaleLine>) : RecyclerView.Adapter<LinesDetailsAdapter.MuroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuroViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_sale_lines, parent, false)

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
        fun bindCliente(line: SaleLine) {
            var txt1: TextView = itemView.findViewById(R.id.txtNombreProductoLine) as TextView
            var txt2: TextView = itemView.findViewById(R.id.txtCantidadProducto) as TextView
            var txt3: TextView = itemView.findViewById(R.id.txtPVP) as TextView
            var txt4: TextView = itemView.findViewById(R.id.txtSubTotalLine) as TextView
            var txt5: TextView = itemView.findViewById(R.id.txtIva) as TextView
            var txt6: TextView = itemView.findViewById(R.id.txtTotalLine) as TextView

            txt1.setText(line.name)
            txt2.setText("Uds: " + line.quantity)
            txt3.setText("PVP: " + line.prize + "€")
            var subtotal : BigDecimal = line.prize.multiply(line.quantity.toBigDecimal())
            txt4.setText("Subtotal: " + subtotal.toString() + "€")
            var cien : Int = 100
            var iva : BigDecimal = (subtotal.multiply(line.iva.toBigDecimal())).divide(cien.toBigDecimal())
            txt5.setText("IVA(" + line.iva +"%): " + iva + "€")
            txt6.setText("Total: " + line.totalAmount + "€")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }



}