package com.happytek.mercaplus.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    val URLAPI = "http://10.0.2.2:8045/"
    var retroFit = Retrofit.Builder()
        .baseUrl(this.URLAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
    }

    fun get(): Retrofit? {
        return retroFit
    }

}