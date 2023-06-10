package com.happytek.mercaplus.service


import com.happytek.mercaplus.model.*
import retrofit2.Call
import retrofit2.http.*

interface Services {

    //CLIENTES

    @GET ("private/client/getBasic")
    abstract fun getClientes(): Call<List<ClienteLista>>

    @GET ("private/client/getBasicByName/{name}")
    abstract fun getClientesByName(@Path("name") name: String): Call<List<ClienteLista>>

    @GET("private/client/get/{id}")
    fun getCliente(@Path("id") id: Int): Call<Cliente>

    @Headers("Content-Type: application/json")
    @POST("/private/client/add")
    fun addClient(@Body cliente: Cliente): Call<StandarResponse>

    //PRODUCTOS

    @GET ("/private/product/get")
    abstract fun getProducts(): Call<List<Producto>>

    @GET ("/private/product/get/{id}")
    abstract fun getProduct(@Path("id") id: Int): Call<Producto>

    @GET ("private/product/getByName/{name}")
    abstract fun getProductosByName(@Path("name") name: String): Call<List<Producto>>

    @Headers("Content-Type: application/json")
    @POST("/private/product/add")
    fun addProduct(@Body producto: Producto): Call<StandarResponse>

    //SALES

    @Headers("Content-Type: application/json")
    @POST("/private/app/add")
    fun addProduct(@Body saleForm: SaleForm): Call<StandarResponse>

    @GET ("private/app/sales/getByClient/{id}")
    abstract fun getSalesByClient(@Path("id") id: Int): Call<List<Sale>>

    @GET ("private/app/sales/get")
    abstract fun getSales(): Call<List<Sale>>

    @GET ("private/app/salelines/getBySale/{id}")
    abstract fun getSalesLinesBySale(@Path("id") id: Int): Call<List<SaleLine>>

    @Headers("Content-Type: application/json")
    @POST("/private/app/sales/paid/{id}")
    fun setPaid(@Path("id") id: Int): Call<StandarResponse>

    //LOCATION

    @GET ("private/location/region/get")
    abstract fun getRegions(): Call<List<Region>>

    @GET ("private/location/province/get")
    abstract fun getProvincias(): Call<List<Province>>

    @GET ("private/location/province/getByRegion/{id}")
    abstract fun getProvinciasByRegion(@Path("id") id: Int): Call<List<Province>>

    @GET ("private/location/town/getByProvince/{id}")
    abstract fun getTownByProvince(@Path("id") id: Int): Call<List<Town>>




}