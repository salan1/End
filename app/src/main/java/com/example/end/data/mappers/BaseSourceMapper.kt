package com.example.end.data.mappers

interface BaseSourceMapper<T, F> {
    fun transformDto(entity: T): F
    fun transformModel(model: F): T
}