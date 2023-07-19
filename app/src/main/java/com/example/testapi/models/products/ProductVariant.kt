package com.example.testapi.models.products

data class ProductVariant(

    val id: Long,
    val product_id: Long,
    val title: String,
    val price: String,
    val sku: String?,
    val position: Int,
    val inventory_policy: String,
    val compare_at_price: String?,
    val fulfillment_service: String,
    val inventory_management: String?,
    val option1: String,
    val option2: String?,
    val option3: String?,
    val created_at: String,
    val updated_at: String,
    val taxable: Boolean,
    val barcode: String?,
    val grams: Int,
    val image_id: String?,
    val weight: Int,
    val weight_unit: String,
    val inventory_item_id: Long,
    val inventory_quantity: Int,
    val old_inventory_quantity: Int,
    val presentment_prices: List<Price>,
    val requires_shipping: Boolean,
    val admin_graphql_api_id: String
)
