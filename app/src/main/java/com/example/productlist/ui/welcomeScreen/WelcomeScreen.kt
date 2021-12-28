package com.example.productlist.ui.welcomeScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.productlist.databinding.ActivityWelcomeBinding
import com.example.productlist.ui.productList.ProductList

class WelcomeScreen : AppCompatActivity() {

    val TAG = WelcomeScreen::class.java.simpleName
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler()
        val runnable: Runnable = Runnable() {
            startActivity(Intent(this, ProductList::class.java))
            finish()
        }
        handler.postDelayed(runnable, 2000)

    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)

    }
}