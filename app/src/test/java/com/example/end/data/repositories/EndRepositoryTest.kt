package com.example.end.data.repositories

import com.example.end.Stubs.productResultDtoStub
import com.example.end.data.repositories.datasources.EndDatasource
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.end.domain.models.Result

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class EndRepositoryTest {

    @Mock
    private lateinit var datasource: EndDatasource
    private lateinit var repository: EndRepository

    @BeforeEach
    fun setUp() {
        repository = EndRepository(datasource)
    }

    @Test
    fun getProducts() {
        val productResult = productResultDtoStub()
        whenever(datasource.getProducts()).thenReturn(Single.just(productResult))

        repository.getProducts().test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue {
                it is Result.Success && it.data.products.isNotEmpty()
            }
    }

}