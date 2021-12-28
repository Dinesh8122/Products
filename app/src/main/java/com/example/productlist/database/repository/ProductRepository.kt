package com.example.productlist.database.repository

import android.util.Log
import com.example.productlist.database.local.ProductDao
import com.example.productlist.database.remote.RemoteDataSource
import com.example.productlist.performGetOperation
import com.example.productlist.performLocalDBOperation
import com.example.productlist.performLocalUpdateOperation
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val productLocalDataSource: ProductDao,
    private val TAG: String = "ProductRepository"
) {


    fun getProducts(id:String) = performGetOperation(
        databaseQuery = { productLocalDataSource.getProducts() },
        networkCall = { remoteDataSource.getProducts(id) },
        saveCallResult = {
            Log.i(TAG, "getProducts: response ${it}")
            val response = it
            productLocalDataSource.insertProducts(response.products)
        }
    )

    fun updateProduct(productId:String,quantity:Int) = performLocalUpdateOperation(
        updateData = quantity,
        databaseQuery = { productLocalDataSource.getProduct(productId) },
        saveCallResult = {
            Log.i(TAG, "updateProduct: $it ")
            productLocalDataSource.updateQuantity(productId,quantity)
        })

    fun getProductsFromLocalDb() =
        performLocalDBOperation(
            databaseQuery = {
                productLocalDataSource.getProducts()
            },
        )


}