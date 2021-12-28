package com.example.productlist.database.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.productlist.database.entities.Products


@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getProducts() : LiveData<List<Products>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Products>)

    @Query("SELECT * FROM products  WHERE id =:productId")
    fun getProduct(productId:String) : LiveData<Products>

    @Query("UPDATE products SET qty =:quantity  WHERE id = :productId")
    fun updateQuantity(productId:String,quantity :Int)

}