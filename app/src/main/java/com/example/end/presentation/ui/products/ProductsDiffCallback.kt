package com.example.end.presentation.ui.products

import androidx.recyclerview.widget.DiffUtil
import com.example.end.domain.models.ProductModel

class ProductsDiffCallback(
    private val oldProducts: List<ProductModel>,
    private val newProducts: List<ProductModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        (oldProducts[oldItemPosition].id.toString() + oldProducts[oldItemPosition].name) ==
                (newProducts[newItemPosition].id.toString() + newProducts[newItemPosition].name)

    override fun getOldListSize(): Int = oldProducts.size

    override fun getNewListSize(): Int = newProducts.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldProducts[oldItemPosition] == newProducts[newItemPosition]

}