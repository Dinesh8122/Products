package com.example.productlist.ui.productList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.productlist.databinding.ActivityProductListBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productlist.database.entities.Products
import com.example.productlist.database.remote.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import android.view.Menu
import com.example.productlist.R
import android.view.MenuItem
import com.example.productlist.ui.cart.CardList


@AndroidEntryPoint
class ProductList : AppCompatActivity(), ProductListAdapter.ProductListItemListener {

    val TAG = ProductList::class.java.simpleName
    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductListAdapter
    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        initRecyclerView()
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.fetchProduct.value = "5def7b172f000063008e0aa2"
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.card_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_card) {
            startActivity(Intent(this,CardList::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        adapter = ProductListAdapter(this)
        binding.productRecyclerview.layoutManager = GridLayoutManager(this, 2)
        binding.productRecyclerview.adapter = adapter
    }



    private fun setupObservers() {

        viewModel.getProducts.observe(this, Observer {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.i(TAG, "setupObservers: getProduct response")
                    if (it.data == null) {
                        return@Observer
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.productRecyclerview.visibility = View.VISIBLE
                    if(it.data.isNullOrEmpty()){
                        binding.productRecyclerview.visibility = View.GONE
                        binding.noProductViewLayout.visibility = View.VISIBLE
                    }else{
                        binding.noProductViewLayout.visibility = View.GONE
                        binding.productRecyclerview.visibility = View.VISIBLE
                        adapter.setProducts(it.data as ArrayList<Products>)
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i(TAG, "setupObservers: getProduct response error ${it.message}")
                }
                Resource.Status.LOADING -> {
                    binding.productRecyclerview.visibility = View.GONE
                    binding.noProductViewLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

        viewModel.updateProduct.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.i(TAG, "setupObservers: updateProduct response")
                    if (it.data == null) {
                        return@Observer
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(
                        this,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i(TAG, "setupObservers: updateProduct response error ${it.message}")
                }
                Resource.Status.LOADING -> {
                    Log.i(TAG, "setupObservers: updateProduct isLoading")
                }
            }
        })


    }

    override fun productItemListener(productId:String,quantity:Int) {
        viewModel.productId.value = productId
        viewModel.quantity.value = quantity
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}