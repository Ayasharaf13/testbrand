package com.example.testapi.models.products

data class ProductOption(
    val id: Long,
    val product_id: Long,
    val name: String,
    val position: Int,
    val values: List<String>
)
