package com.example.testapi.models.brands

import com.example.testapi.Constants
import com.example.testapi.models.products.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ShoppingService {

    @Headers("X-Shopify-Access-Token: ${Constants.accessToken}")
    @GET("admin/api/2023-07/smart_collections.json")
    suspend fun getBrands() : Response<BrandList>


    @Headers("X-Shopify-Access-Token: ${Constants.accessToken}")
    @GET("admin/api/2023-07/collections/{id}/products.json")
    suspend fun getCollectionProducts(@Path(value = "id") id:Long) : Response<Products>

}