package com.happytek.mercaplus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Town (var id : Int? , var name : String?, var cp: String?, var province: Province?) : Parcelable{
}