package com.example.myapplication.Services

import com.example.stores.Model.Stores.Data
import com.example.stores.Model.Stores.Store
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface StoreApi {
    // GET /Store
    @GET("api/stores")
    fun getStores(): Call<Store>
//
//    // GET /Product/{id}
//    @GET("api/products/{id}")
//    fun getProduct(@Path("id") id:Int): Call<Data>
//
//    // POST /Product
//    @FormUrlEncoded
    @Multipart
    @POST("api/stores.json")
    fun addStore(
    @Part("store[name]") name: RequestBody?,
    @Part("store[tagline]") tagline: RequestBody?,
    @Part("store[description]") description: RequestBody?,
    @Part("store[street]") street: RequestBody?,
    @Part("store[zipcode]") zipcode: RequestBody?,
    @Part("store[city]")city:RequestBody?,
    @Part("store[state]")state:RequestBody?,
    @Part("store[country]")country:RequestBody?,
    @Part("store[phone]")phone:RequestBody?
//    @Part("store[logoUrl]")logoUrl:RequestBody?,
//    @Part("store[coverUrl]")coverUrl:RequestBody?
    ): Call<Data>
//
//
//    // PATCH /students
//    @FormUrlEncoded
    @Multipart
    @PATCH("api/stores/{id}")
    fun updateStore(
//        @Query("page") page: Int,
        @Path("id") id:Int,
        @Part("store[name]") name: String,
        @Part("store[tagline]") tagline: String,
        @Part("store[description]") description: String,
        @Part("store[street]") street: String?,
        @Part("store[zipcode]") zipcode: String,
        @Part("store[city]")city:String,
        @Part("store[state]")state:String?,
        @Part("store[country]")country:String,
        @Part("store[phone]")phone:String
    //    @Part("store[logoUrl]")logoUrl:RequestBody?,
    //    @Part("store[coverUrl]")coverUrl:RequestBody?
    ): Call<Data>

    // DELETE /Product/{id}
    @DELETE("api/stores/{id}")
    fun deleteStore(@Path("id") id:Int): Call<Store>
}