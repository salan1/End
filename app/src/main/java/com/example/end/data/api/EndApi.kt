package com.example.end.data.api

import com.example.end.data.entities.ProductsResultDto
import io.reactivex.Single
import retrofit2.http.GET

interface EndApi {
    @GET("media/catalog/example.json")
    fun getProducts(): Single<ProductsResultDto>
}