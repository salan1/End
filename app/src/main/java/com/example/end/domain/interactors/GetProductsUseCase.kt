package com.example.end.domain.interactors

import com.example.end.data.repositories.EndRepository
import com.example.end.domain.interactors.base.BaseUseCaseSingle
import com.example.end.domain.interactors.base.Params
import com.example.end.domain.models.ProductsModel
import com.example.end.domain.models.Result
import io.reactivex.Single
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val endRepository: EndRepository
) : BaseUseCaseSingle<Result<ProductsModel>>() {

    override fun getSingle(params: Params): Single<Result<ProductsModel>> =
        endRepository.getProducts()

}