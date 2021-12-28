package com.example.productlist.database.entities

import androidx.room.*
import com.example.productlist.Converters


data class ProductsRes (
    val products: List<Products>
)

@Entity(tableName = "products")
data class Products (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    val name: String,
    val product_id: String,
    val sku: String,
    val image: String,
    val thumb: String,
    val zoom_thumb: String,
    @TypeConverters(Converters::class)
    val options: List<String>,
    val description: String,
    val href: String,
    val quantity: Int,
    val qty: Int,
    @TypeConverters(Converters::class)
    val images: List<String>,
    val price: String,
    val special: String
)


data class ErrorResponse(
    val message: String? = null,
    val success: Boolean,
    val error: String? = null,
)
