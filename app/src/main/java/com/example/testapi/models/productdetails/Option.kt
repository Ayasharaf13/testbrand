package com.example.testapi.models.productdetails

data class Option(
    val id: Long?,
    val name: String?,
    val position: Int?,
    val product_id: Long?,
    val values: List<String>?
)