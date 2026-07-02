package com.example.productcatalog.products.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productcatalog.R
import com.example.productcatalog.products.presentation.ProductUiItem
import java.util.Locale

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.product_name)
    private val categoryTextView: TextView = itemView.findViewById(R.id.product_category)
    private val priceTextView: TextView = itemView.findViewById(R.id.product_price)

    fun bind(product: ProductUiItem){
        nameTextView.text = product.name
        categoryTextView.text = product.category
        val priceFormatted = String.format(Locale.getDefault(), "$%.2f", product.priceInCents / 100.0)
        priceTextView.text = priceFormatted
    }
}