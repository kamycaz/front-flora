package com.happytek.mercaplus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Province (var id: Int, var name : String, var region: Region?) :Parcelable {
}