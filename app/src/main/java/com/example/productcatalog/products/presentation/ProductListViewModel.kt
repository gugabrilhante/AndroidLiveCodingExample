package com.example.productcatalog.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.products.domain.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsUiState())
    val state: StateFlow<ProductsUiState> = _state.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            productsRepository.getAllProductsFlow().collect { result ->
                result.fold(
                    onSuccess = { products ->
                        val uiItems = products.map { model ->
                            ProductUiItem(
                                id = model.id,
                                name = model.name,
                                category = model.category,
                                priceInCents = model.priceInCents
                            )
                        }
                        _state.update { it.copy(isLoading = false, products = uiItems) }
                    },
                    onFailure = {
                        _state.update { it.copy(isLoading = false) }
                    }
                )
            }
        }
    }
}