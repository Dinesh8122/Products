package com.example.productlist.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.productlist.database.entities.Products
import com.example.productlist.database.remote.Resource
import com.example.productlist.database.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {


    val TAG = ProductViewModel::class.java.simpleName
    val fetchProduct = MutableLiveData<String>()
    val productId = MutableLiveData<String>()
    val quantity = MutableLiveData<Int>()


    private val _getProducts = fetchProduct.switchMap { value ->
        repository.getProducts(value)
    }
    val getProducts:  LiveData<Resource<List<Products>>> = _getProducts


    private val _updateProduct = quantity.switchMap { value ->
        repository.updateProduct(productId.value.toString(),value)
    }
    val updateProduct:  LiveData<Resource<Products>> = _updateProduct


}