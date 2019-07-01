package com.example.end.data.entities

import com.google.gson.annotations.SerializedName

data class ProductsResultDto(
    @SerializedName("products")
    val products: List<ProductDto>,
    @SerializedName("title")
    val title: String,
    @SerializedName("product_count")
    val productCount: Int
)