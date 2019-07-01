package com.example.end

import com.example.end.data.entities.ProductDto
import com.example.end.data.entities.ProductsResultDto
import com.example.end.domain.models.ProductModel
import com.example.end.domain.models.ProductsModel
import net.andreinc.mockneat.MockNeat

object Stubs {

    private val mock: MockNeat = MockNeat.threadLocal()

    fun productDtoStub(): ProductDto = ProductDto(
        mock.ints().range(10, 1000).get(),
        mock.strings().size(10).get(),
        mock.currencies().symbol().get() + mock.ints().range(1, 50).get(),
        mock.urls().get()
    )

    fun productResultDtoStub(): ProductsResultDto {
        val products = ArrayList<ProductDto>()
        for (i in 0 until mock.ints().range(10, 100).get()) {
            products.add(productDtoStub())
        }
        return ProductsResultDto(
            products.toList(),
            mock.strings().size(10).get(),
            products.size
        )
    }

    fun productModelStub(): ProductModel =
        ProductModel(
            mock.ints().range(1, 1000).get(),
            mock.strings().size(10).get(),
            mock.currencies().symbol().get() + mock.ints().range(1, 50).get(),
            mock.urls().get()
        )

    fun productsModelStub(): ProductsModel {
        val products = ArrayList<ProductModel>()
        for (i in 0 until mock.ints().range(10, 100).get()) {
            products.add(productModelStub())
        }
        return ProductsModel(
            mock.strings().size(10).get(),
            products
        )
    }


    /**
     * data class ProductsModel(
    val title: String,
    val products: List<ProductModel>
    )
     */

    /**
     * data class ProductModel(
    val id: Int,
    val name: String,
    val price: String,
    val image: String
    )
     */


}