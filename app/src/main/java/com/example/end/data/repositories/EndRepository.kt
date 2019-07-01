package com.example.end.data.repositories

import com.example.end.data.mappers.ProductsMapper
import com.example.end.data.repositories.datasources.EndDatasource
import com.example.end.domain.models.ProductsModel
import com.example.end.domain.models.Result
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EndRepository @Inject constructor(
    private val datasource: EndDatasource
) {

    fun getProducts(): Single<Result<ProductsModel>> =
        datasource.getProducts().map<Result<ProductsModel>> {
            Result.Success(ProductsMapper.transformDto(it))
        }.onErrorReturn {
            Result.Error(it)
        }

}