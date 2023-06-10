package com.happytek.mercaplus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Region (val id : Int, val name : String) : Parcelable {
}