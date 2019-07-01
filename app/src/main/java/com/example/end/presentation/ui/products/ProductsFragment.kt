package com.example.end.presentation.ui.products

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.end.R
import com.example.end.domain.models.ProductModel
import com.example.end.domain.models.Result
import com.example.end.presentation.presenters.impl.ProductViewModel
import com.example.end.presentation.ui.MainActivity
import com.example.end.presentation.ui.base.BaseFragment
import com.example.end.presentation.widgets.HintSpinner
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.product_list_header.*
import javax.inject.Inject


class ProductsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel::class.java)
        viewModel.getProducts()
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductsAdapter {
            // Process click event here
        }
        recyclerView.adapter = adapter

        val act = activity as MainActivity
        act.setDrawerEnabled(true)
        textView_filter.setOnClickListener {
            act.openDrawer()
        }
        setupSortSpinner()
        setupViewSpinner()

        viewModel.productsLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    textView_items.text = resources.getString(
                        R.string.product_items,
                        result.data.products.size.toString()
                    )
                    setupRecyclerView(result.data.products, viewModel.viewStyle)
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    displayError(result.exception.message)
                }
                is Result.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupSortSpinner() {
        context?.also { context ->
            val sortList = context.resources?.getStringArray(R.array.menu_sort_values)?.toList()
            sortList?.also {
                spinner_sort.setItems(it, HintSpinner.HintMode.NORMAL)
            }
        }
    }

    private fun setupViewSpinner() {
        context?.also { context ->
            val viewList = context.resources?.getStringArray(R.array.menu_view_values)?.toList()
            viewList?.also {
                spinner_view.setItems(
                    it,
                    HintSpinner.HintMode.MULTI,
                    viewModel.viewStyle
                )

                spinner_view.selectListener = { pos ->
                    viewModel.productsLiveData.value?.also { result ->
                        if (result is Result.Success) {
                            when (pos) {
                                2 -> {
                                    viewModel.viewStyle = false
                                    updateRecyclerView(result.data.products, viewModel.viewStyle)
                                }
                                3 -> {
                                    viewModel.viewStyle = true
                                    updateRecyclerView(result.data.products, viewModel.viewStyle)
                                }
                                else -> {

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(products: List<ProductModel>, grid: Boolean) {
        if (grid) {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            adapter.imageHeight = dpToPixel(context!!, getScreenHeight() / 3f)
            adapter.updateProducts(products)
        } else {
            adapter.imageHeight = dpToPixel(context!!, getScreenHeight() / 3f)
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter.updateProducts(products)
        }
    }

    private fun getScreenHeight(): Float {
        val display = activity!!.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        return outMetrics.heightPixels / density
    }

    fun dpToPixel(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    private fun updateRecyclerView(products: List<ProductModel>, grid: Boolean) {
        setupRecyclerView(products, grid)
    }

    private fun displayError(message: String?) {
        message?.also {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun getArgs(_bundle: Bundle) {
    }

}