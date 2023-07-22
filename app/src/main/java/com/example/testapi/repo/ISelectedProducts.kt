package com.example.testapi.repo

import com.example.testapi.models.products.CollectProductsModel
import kotlinx.coroutines.flow.Flow

interface ISelectedProducts {
    suspend fun getSelectedProducts(ids :String): Flow<CollectProductsModel?>

}