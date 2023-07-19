package com.example.testapi.models.brands

import com.example.testapi.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShoppingClient {
    lateinit var shoppingService: ShoppingService
    val BASE_URL = "https://${Constants.storeUrl}"
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