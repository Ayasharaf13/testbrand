package com.example.testapi.models.brands

import com.example.testapi.Constants
import com.example.testapi.models.products.CollectProductsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ShoppingService {

    @Headers("X-Shopify-Access-Token: ${Constants.accessToken}")
    @GET("admin/api/2023-07/smart_collections.json")
    suspend fun getBrands() : Response<BrandList>

    @Headers("X-Shopify-Access-Token: ${Constants.accessToken}")
    @GET("admin/api/2023-07/products.json")
    suspend fun getSelectedProductsDetails(@Query("ids") id: String) : Response<CollectProductsModel>



    @Headers("X-Shopify-Access-Token: ${Constants.accessToken}")
    @GET("admin/api/2023-07/collections/{id}/products.json")
    suspend fun getCollectionProducts(@Path(value = "id") id:Long) : Response<CollectProductsModel>

/*
    @Headers("X-Shopify-Access-Token: ${Constants.accessToken}")
    @GET("admin/api/2023-07/collections/{id}/products.json")
    suspend fun getCollectionProducts(@Path(value = "id") id:Long) : Response<Products>

 */

}