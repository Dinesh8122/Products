package com.example.productlist.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productlist.database.entities.Products
import com.example.productlist.database.remote.Resource
import com.example.productlist.databinding.ActivityCardListBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class CardList : AppCompatActivity() {

    val TAG = CardList::class.java.simpleName
    private lateinit var binding: ActivityCardListBinding
    private lateinit var adapter: CardAdapter
    private lateinit var viewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        initRecyclerView()
        viewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        viewModel.fetchProduct.value = "5def7b172f000063008e0aa2"
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun initRecyclerView() {
        adapter = CardAdapter()
        binding.cardRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.cardRecyclerview.adapter = adapter
    }

    private fun setupObservers() {

        viewModel.getProducts.observe(this, Observer {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.i(TAG, "setupObservers: getProduct response")
                    binding.progressBar.visibility = View.GONE
                    binding.cardRecyclerview.visibility = View.VISIBLE
                    if (it.data == null) {
                        return@Observer
                    }
                    if(it.data.isNullOrEmpty()){
                        binding.cardRecyclerview.visibility = View.GONE
                        binding.noCardViewLayout.visibility = View.VISIBLE
                    }else{
                        binding.noCardViewLayout.visibility = View.GONE
                        binding.cardRecyclerview.visibility = View.VISIBLE
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
                    binding.cardRecyclerview.visibility = View.GONE
                    binding.noCardViewLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}