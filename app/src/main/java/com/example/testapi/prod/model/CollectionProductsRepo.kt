package com.example.testapi.prod.model

import com.example.testapi.models.products.CollectProductsModel
import kotlinx.coroutines.flow.Flow

/*
class CollectionProductsRepo {


}

 */


class CollectionProductsRepo(var remoteSource: ICollectionProductsRepo ): ICollectionProductsRepo {
    override suspend fun getCollectionProducts(id: Long): Flow<CollectProductsModel?> {
        return remoteSource.getCollectionProducts(id)
    }

    override suspend fun getSelectedProducts(ids: String): Flow<CollectProductsModel?> {
        return remoteSource.getSelectedProducts(ids)
    }

}