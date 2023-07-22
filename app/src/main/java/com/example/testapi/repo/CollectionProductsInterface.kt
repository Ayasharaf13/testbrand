package com.example.testapi.repo

import com.example.testapi.models.products.CollectProductsModel
import kotlinx.coroutines.flow.Flow

interface CollectionProductsInterface {
    suspend fun getCollectionProducts(id:Long) : Flow<CollectProductsModel?>

}