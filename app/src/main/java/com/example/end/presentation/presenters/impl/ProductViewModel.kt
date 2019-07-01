package com.example.end.presentation.presenters.impl

import androidx.lifecycle.MutableLiveData
import com.example.end.domain.interactors.GetProductsUseCase
import com.example.end.domain.interactors.base.Params
import com.example.end.domain.models.ProductsModel
import com.example.end.domain.models.Result
import com.example.end.presentation.presenters.BaseViewModel
import com.example.end.presentation.ui.products.ProductsFragment
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel<ProductsFragment>() {

    val productsLiveData = MutableLiveData<Result<ProductsModel>>()
    var viewStyle = true

    fun getProducts() {
        productsLiveData.postValue(Result.Loading)
        mCompositeDisposable += getProductsUseCase.execute(Params.EMPTY)
            .subscribeBy(
                onSuccess = {
                    productsLiveData.postValue(it)
                },
                onError = {
                    productsLiveData.postValue(Result.Error(it))
                }
            )
    }


}