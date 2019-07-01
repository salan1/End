package com.example.end.domain.interactors

import com.example.end.Stubs.productsModelStub
import com.example.end.data.repositories.EndRepository
import com.example.end.domain.interactors.base.Params
import com.example.end.domain.models.Result
import com.example.end.rules.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class, RxImmediateSchedulerRule::class)
internal class GetProductsUseCaseTest {

    @Mock
    private lateinit var repository: EndRepository
    private lateinit var getProductsUseCase: GetProductsUseCase

    @BeforeEach
    fun setUp() {
        getProductsUseCase = GetProductsUseCase(repository)
    }

    @Test
    fun getSingle() {
        val productsResult = Result.Success(productsModelStub())
        whenever(repository.getProducts()).thenReturn(Single.just(productsResult))
        getProductsUseCase.execute(Params.EMPTY).test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue {
                it is Result.Success && it.data == productsResult.data
            }
    }

}