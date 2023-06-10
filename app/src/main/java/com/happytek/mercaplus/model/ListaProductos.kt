package com.happytek.mercaplus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class ListaProductos (val list : ArrayList<Producto>) : Parcelable {
}