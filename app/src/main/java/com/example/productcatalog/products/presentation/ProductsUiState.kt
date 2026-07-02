package com.example.productcatalog.products.presentation

data class ProductsUiState(
    val isLoading: Boolean = true,
    val products: List<ProductUiItem> = emptyList()
)
