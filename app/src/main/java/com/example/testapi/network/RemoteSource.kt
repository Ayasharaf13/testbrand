package com.example.testapi.models.brands

import com.example.testapi.home.model.InterfaceBrands
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/*
class RemoteSource {
}

 */



object ShopifyAPi {

   val retrofitService: ShoppingService by lazy {
       ShoppingClient.getInstance().retrofitInstance.create(ShoppingService::class.java)

    }
}
class RemoteSource() : InterfaceBrands {

    var network: ShoppingService = ShopifyAPi.retrofitService
    override suspend fun getBrands(): Flow<BrandList?> {
        return flowOf(network.getBrands().body())
    }
/*
    override suspend fun getCollectionProducts(id: Long): Flow<Produts?> {
        return flowOf(network.getCollectionProducts(id).body())
    }

 */

}
