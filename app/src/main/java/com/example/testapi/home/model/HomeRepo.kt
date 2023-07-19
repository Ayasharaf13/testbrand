package com.example.testapi.home.model

import com.example.testapi.models.brands.BrandList
import kotlinx.coroutines.flow.Flow

/*
class HomeRepo {
}

 */



class HomeRepo(var remoteSource: InterfaceBrands) :InterfaceBrands {
    override suspend fun getBrands(): Flow<BrandList?> {
        return remoteSource.getBrands()
    }
}
