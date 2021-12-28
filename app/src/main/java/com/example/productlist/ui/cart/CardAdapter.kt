package com.example.productlist.ui.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.productlist.database.entities.Products
import com.example.productlist.databinding.CardListItemBinding
import com.squareup.picasso.Picasso
import java.util.ArrayList

class CardAdapter: RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private val items = ArrayList<Products>()

    fun setProducts(newItems: ArrayList<Products>) {
        val diffCallback = CardDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: CardListItemBinding =
            CardListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(parent.context, binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    class CardViewHolder(
        private val context: Context,
        private val itemBinding: CardListItemBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        val TAG = CardAdapter::class.java.simpleName
        private lateinit var product: Products

        fun bind(item: Products) {
            this.product = item
            itemBinding.productName.text = item.name
            itemBinding.productPriceDisplay.text = item.price
            itemBinding.quantityDisplay.text = item.qty.toString()

            Picasso.get()
                .load(item.image)
                .fit().centerInside()
                .into(itemBinding.productImage, object : com.squareup.picasso.Callback {
                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                        Log.d(TAG, "onError: Loading picture")
                    }


                    override fun onSuccess() {

                    }


                })
        }

    }
}

class CardDiffCallback(
    private val oldList: List<Products>,
    private val newList: List<Products>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProductId = oldList[oldItemPosition].id
        val newProductId = newList[newItemPosition].id
        return oldProductId == newProductId
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct = oldList[oldItemPosition]
        val newProduct = newList[newItemPosition]
        return oldProduct.id == newProduct.id

    }
}