package com.example.end.data.api

import com.example.end.BuildConfig
import com.google.gson.Gson
import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class EndClient(private val httpUrl: HttpUrl? = null) {

    private val pool = ConnectionPool(2, 2, TimeUnit.MINUTES)

    val endApi: EndApi =
        configRetrofit(httpUrl, "https://www.endclothing.com").create(EndApi::class.java)

    private fun configRetrofit(httpUrl: HttpUrl?, baseUrl: String): Retrofit {
        return if (httpUrl != null) {
            Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(configClient())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        } else {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(configClient())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }

    private fun configClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClient.connectionPool(pool)

        if (BuildConfig.DEBUG) {
            //Debugging interceptor
            val loggingInterceptor: Interceptor = Interceptor {
                val request: Request = it.request()
                val response: Response = it.proceed(request)
                val responseBody: ResponseBody = response.body()!!
                val source: BufferedSource = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body
                val buffer: Buffer = source.buffer()
                val utf8: Charset = Charset.forName("UTF-8")
                Timber.i("REQUEST_URL: %s", request.toString())
                Timber.i("RESPONSE_JSON: %s", buffer.clone().readString(utf8))
                Timber.i("RESPONSE: %s", response.toString())
                response
            }
            okHttpClient.addInterceptor(loggingInterceptor)
        }
        return okHttpClient.build()
    }
}