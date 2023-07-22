package com.example.testapi.repo

import com.example.testapi.home.model.InterfaceBrands
import com.example.testapi.prod.model.CollectionProductsRepo
import com.example.testapi.prod.model.ICollectionProductsRepo

interface RemoteSourceInterface:InterfaceBrands,CollectionProductsInterface,ISelectedProducts
,ICollectionProductsRepo{
}