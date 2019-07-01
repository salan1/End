package com.example.end.data.repositories.datasources

import com.example.end.data.api.EndApi
import com.example.end.data.entities.ProductsResultDto
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EndDatasource @Inject constructor(private val client: EndApi) {

    fun getProducts(): Single<ProductsResultDto> =
        client.getProducts()

}