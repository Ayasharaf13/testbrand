package com.example.testapi.models.brands

import com.example.testapi.home.model.InterfaceBrands
import com.example.testapi.models.products.CollectProductsModel
import com.example.testapi.repo.RemoteSourceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/*
class RemoteSource {
}

 */
sealed class BrandsResult {
    data class Success(val brandList: BrandList?) : BrandsResult()
    data class Error(val message: String) : BrandsResult()
}


object ShopifyAPi {

   val retrofitService: ShoppingService by lazy {
       ShoppingClient.getInstance().retrofitInstance.create(ShoppingService::class.java)

    }
}
class RemoteSource() : RemoteSourceInterface {

    var network: ShoppingService = ShopifyAPi.retrofitService
    override suspend fun getBrands(): Flow<BrandList?> {
        return flowOf(network.getBrands().body())
    }

    override suspend fun getCollectionProducts(id: Long): Flow<CollectProductsModel?> {

        return flowOf(network.getCollectionProducts(id).body())

    }

    override suspend fun getSelectedProducts(ids: String): Flow<CollectProductsModel?> {
        return flowOf(network.getSelectedProductsDetails(ids).body())

    }

}














/*
    override suspend fun getCollectionProducts(id: Long): Flow<Produts?> {
        return flowOf(network.getCollectionProducts(id).body())
    }

 */


