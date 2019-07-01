package com.example.end.presentation.presenters.impl

import com.example.end.Stubs.productsModelStub
import com.example.end.domain.interactors.GetProductsUseCase
import com.example.end.domain.interactors.base.Params
import com.example.end.domain.models.Result
import com.example.end.rules.InstantTaskExecutorExtension
import com.example.end.rules.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(
    MockitoExtension::class,
    RxImmediateSchedulerRule::class,
    InstantTaskExecutorExtension::class
)
internal class ProductViewModelTest {

    @Mock
    lateinit var getProductsUseCase: GetProductsUseCase
    lateinit var productViewModel: ProductViewModel

    @BeforeEach
    fun setUp() {
        productViewModel = ProductViewModel(getProductsUseCase)
    }

    @Test
    fun getProducts() {
        val productsModel = productsModelStub()
        whenever(getProductsUseCase.execute(any<Params>()))
            .thenReturn(Single.just(Result.Success(productsModel)))
        productViewModel.getProducts()
        assert(
            productViewModel.productsLiveData.value is Result.Success &&
                    (productViewModel.productsLiveData.value as Result.Success).data == productsModel
        )
    }
}