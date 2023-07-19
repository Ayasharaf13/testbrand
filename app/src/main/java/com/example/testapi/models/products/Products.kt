package com.example.testapi.models.products

data class Products(

    val id: Long,
    val title: String,
    val body_html: String,
    val vendor: String,
    val product_type: String,
    val created_at: String,
    val handle: String,
    val updated_at: String,
    val published_at: String?,
    val template_suffix: String?,
    val status: String,
    val published_scope: String,
    val tags: String,
    val admin_graphql_api_id: String,
    val variants: List<ProductVariant>,
    val options: List<ProductOption>,
    val images: List<String>, // You can change the type of "images" property based on the actual data type returned in the JSON response.
    val image: String?
)
