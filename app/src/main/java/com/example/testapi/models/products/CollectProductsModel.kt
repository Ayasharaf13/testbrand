package com.example.testapi.models.products

import com.example.testapi.models.productdetails.Product


data class CollectProductsModel(
    val products: List<Product>
)