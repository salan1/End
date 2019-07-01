package com.example.end.data.mappers

import com.example.end.data.entities.ProductDto
import com.example.end.domain.models.ProductModel

object ProductMapper : BaseSourceMapper<ProductDto, ProductModel> {

    override fun transformDto(entity: ProductDto): ProductModel =
        ProductModel(
            entity.id,
            entity.name,
            entity.price,
            entity.image
        )

    override fun transformModel(model: ProductModel): ProductDto =
        ProductDto(
            model.id,
            model.name,
            model.price,
            model.image
        )
}