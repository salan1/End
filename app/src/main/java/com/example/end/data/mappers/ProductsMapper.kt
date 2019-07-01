package com.example.end.data.mappers

import com.example.end.data.entities.ProductsResultDto
import com.example.end.domain.models.ProductsModel

object ProductsMapper : BaseSourceMapper<ProductsResultDto, ProductsModel> {

    override fun transformDto(entity: ProductsResultDto): ProductsModel =
        ProductsModel(
            entity.title,
            entity.products.map {
                ProductMapper.transformDto(it)
            }
        )

    override fun transformModel(model: ProductsModel): ProductsResultDto =
        ProductsResultDto(
            model.products.map {
                ProductMapper.transformModel(it)
            },
            model.title,
            model.products.size
        )

}