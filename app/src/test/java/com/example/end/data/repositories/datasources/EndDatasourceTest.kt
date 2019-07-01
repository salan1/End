package com.example.end.data.repositories.datasources

import com.example.end.MockUtils.serielseObject
import com.example.end.Stubs.productResultDtoStub
import com.example.end.data.api.EndApi
import com.example.end.data.api.EndClient
import net.andreinc.mockneat.MockNeat
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal class EndDatasourceTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var datasource: EndDatasource
    private lateinit var mockedDatasource: EndDatasource
    private lateinit var realServer: EndApi


    @BeforeEach
    fun setUp() {
        mockServer = MockWebServer()

        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val client = retrofit.create(EndApi::class.java)
        mockedDatasource = EndDatasource(client)
        realServer = EndClient().endApi
        datasource = EndDatasource(realServer)
    }

    @AfterEach
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun getProducts() {
        datasource.getProducts().test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue {
                it.productCount > 0
            }
    }

    @Test
    fun getMockedProducts() {
        val response = productResultDtoStub()
        mockServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(serielseObject(response))
        )

        mockedDatasource.getProducts().test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue {
                it == response
            }
    }

}