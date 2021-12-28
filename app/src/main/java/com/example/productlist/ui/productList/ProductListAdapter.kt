package com.example.productlist.ui.productList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.productlist.database.entities.Products
import com.example.productlist.databinding.ProductListItemBinding
import com.squareup.picasso.Picasso
import java.util.*


class ProductListAdapter(private val listener: ProductListItemListener) :
    RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>(
    ) {


    interface ProductListItemListener {
        fun productItemListener(productId:String,quantity:Int)
    }


    private val items = ArrayList<Products>()

    fun setProducts(newItems: ArrayList<Products>) {
        val diffCallback = ProductsDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding: ProductListItemBinding =
            ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(parent.context, binding, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    class ProductListViewHolder(
        private val context: Context,
        private val itemBinding: ProductListItemBinding,
        private val listener: ProductListItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        val TAG = ProductListAdapter::class.java.simpleName
        private lateinit var product: Products
        private var selectedPosition : Int = 0
        private var quantity : Int = 0

        fun bind(item: Products) {

            this.product = item

            itemBinding.productName.text = item.name
            itemBinding.productPrice.text = item.price

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

            itemBinding.plusButton.setOnClickListener {
                selectedPosition = position
                if(selectedPosition == position){
                    quantity += 1
                    itemBinding.quantityDisplay.text = quantity.toString()
                    selectedPosition = position
                    listener.productItemListener(item.id,quantity)

                }
            }
            itemBinding.minusButton.setOnClickListener {
                selectedPosition = position
                if(selectedPosition == position && quantity !=0){
                    quantity -= 1
                    itemBinding.quantityDisplay.text = quantity.toString()
                    selectedPosition = position
                    listener.productItemListener(item.id,quantity)
                }
            }

        }

    }
}

class ProductsDiffCallback(
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