package com.example.productcatalog.products.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productcatalog.R
import com.example.productcatalog.products.presentation.ProductUiItem

class ProductsAdapter(
    private val products: List<ProductUiItem>
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, p1: Int) {
        holder.bind(products[p1])
    }

    override fun getItemCount(): Int = products.size

}