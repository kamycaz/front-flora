package com.happytek.mercaplus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Producto( val id : Int?, val name : String, val description : String, val prize : BigDecimal , val iva : Int, var cantidad : Int) : Parcelable {
}