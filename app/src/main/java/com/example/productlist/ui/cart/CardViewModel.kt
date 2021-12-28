package com.example.productlist.ui.cart

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
class CardViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    val TAG = CardViewModel::class.java.simpleName
    val fetchProduct = MutableLiveData<String>()


    private val _getProducts = fetchProduct.switchMap { value ->
        repository.getProductsFromLocalDb()
    }
    val getProducts: LiveData<Resource<List<Products>>> = _getProducts

}