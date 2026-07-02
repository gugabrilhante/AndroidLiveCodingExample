package com.example.productcatalog.di

import com.example.productcatalog.data.FakeApiScenario
import com.example.productcatalog.data.FakeProductsApi
import com.example.productcatalog.data.ProductsApi
import com.example.productcatalog.products.data.ProductsRepositoryImpl
import com.example.productcatalog.products.domain.ProductsRepository
import com.example.productcatalog.products.presentation.ProductListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ProductsApi> {
        FakeProductsApi(scenario = FakeApiScenario.SUCCESS)
    }

    single { Dispatchers.IO }

    single<ProductsRepository> {
        ProductsRepositoryImpl(get(), get())
    }

    viewModel { ProductListViewModel(get()) }
}
