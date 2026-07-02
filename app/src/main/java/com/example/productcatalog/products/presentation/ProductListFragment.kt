package com.example.productcatalog.products.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.productcatalog.products.presentation.adapter.ProductsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.example.productcatalog.R

class ProductListFragment : Fragment() {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    private val viewModel: ProductListViewModel by viewModel()

    private lateinit var currencyConverterRecyclerView: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyConverterRecyclerView = view.findViewById(R.id.currencyConverterRecyclerView)
        loadingProgressBar = view.findViewById(R.id.loading)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: ProductsUiState) {
        loadingProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        currencyConverterRecyclerView.adapter = ProductsAdapter(state.products)
    }
}