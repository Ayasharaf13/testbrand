package com.example.testapi.home.model

import com.example.testapi.models.brands.BrandList
import kotlinx.coroutines.flow.Flow

interface InterfaceBrands {
    suspend fun getBrands() : Flow<BrandList?>

}