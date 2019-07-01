package com.example.end.presentation.ui.products

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.end.domain.models.ProductModel

class ProductsAdapter(inline val click: (id: Int) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var products: ArrayList<ProductModel>? = null
    var imageHeight = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(ProductItemView(parent.context).also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        })


    override fun getItemCount(): Int =
        products?.size ?: 0

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        products?.apply {
            this[position].also {
                holder.bindTo(it, position)
            }
        }
    }

    fun updateProducts(products: List<ProductModel>) {
        if (this.products != null) {
            this.products?.also {
                val diffResult = DiffUtil.calculateDiff(ProductsDiffCallback(it, products))
                it.clear()
                it.addAll(products)
                diffResult.dispatchUpdatesTo(this)
            }
        } else {
            this.products = ArrayList(products)
            notifyDataSetChanged()
        }
    }

    inner class ProductViewHolder(private val item: ProductItemView) :
        RecyclerView.ViewHolder(item) {
        fun bindTo(product: ProductModel, pos: Int) {
            item.setTitle(product.name)
            item.setPrice(product.price)
            item.setImageHeight(imageHeight)
            item.setImage(product.image)

            item.setOnClickListener {
                click.invoke(product.id)
            }
        }
    }
}