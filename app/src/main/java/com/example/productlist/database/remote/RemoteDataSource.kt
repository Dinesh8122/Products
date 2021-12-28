package com.example.productlist.database.remote

import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource() {

    suspend fun getProducts(id:String) =
        getResult { apiService.getProducts(id) }
}