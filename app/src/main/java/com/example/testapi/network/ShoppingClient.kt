package com.example.testapi.models.brands

import com.example.testapi.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShoppingClient {

    lateinit var shoppingService: ShoppingService

    val BASE_URL = "https://${Constants.storeUrl}"
  //  val BASE_URL="https://2c733fe978633021e5042d935735e030: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@itp-sv-and7.myshopify.com/admin/api/2023-07/"

    val retrofitInstance = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    companion object {
        private var instance: ShoppingClient? = null

        fun getInstance(): ShoppingClient {
            if (instance == null) {
                instance = ShoppingClient()
            }
            return instance as ShoppingClient
        }
    }
}