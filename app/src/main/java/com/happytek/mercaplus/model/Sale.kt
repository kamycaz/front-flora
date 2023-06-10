package com.happytek.mercaplus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
@Parcelize
class Sale(var id : Int, var createAt : String?, var totalAmount : BigDecimal?, var mainAmount : BigDecimal? , var taxAmount : BigDecimal?, var idClient : Int?, var name : String?, var codInvoice : String?, var paid : Boolean?) : Parcelable  {
}