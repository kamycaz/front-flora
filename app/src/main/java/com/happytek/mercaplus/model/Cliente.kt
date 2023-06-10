package com.happytek.mercaplus.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cliente(var id : Integer? , var name: String?, var phone: String?, var town : Town?, var fiscal : String?, var address : String?, var cp: String?, var cif: String?) : Parcelable{

  /*  var comercialName: String
    var phone: String

    constructor(comercialName : String, telefono : String) {
        this.comercialName = comercialName
        this.phone = telefono
    }

    constructor() {
        this.comercialName = ""
        phone = ""
    } */




}