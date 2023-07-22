package com.example.testapi.home.model

import com.example.testapi.models.brands.BrandList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/*
class HomeRepo {
}

 */

/*

class HomeRepo(var remoteSource: InterfaceBrands) :InterfaceBrands {
    override suspend fun getBrands(): Flow<BrandList?> {
        return remoteSource.getBrands()
    }
}

 */
class HomeRepo(private val remoteSource: InterfaceBrands) : InterfaceBrands {
    override suspend fun getBrands(): Flow<BrandList?> {
        return remoteSource.getBrands().catch { e ->
            // Handle the exception here, for example:
            // Log the error, show an error message, etc.
            emit(null) // Return null to indicate the failure to the caller
        }
    }
}





