package com.example.productlist.database.remote

import com.example.productlist.database.entities.ProductsRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("v2/{id}")
    suspend fun getProducts(
        @Path("id") id: String,
    ): Response<ProductsRes>
}