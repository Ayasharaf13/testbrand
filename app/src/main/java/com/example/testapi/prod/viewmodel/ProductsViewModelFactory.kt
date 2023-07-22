package com.example.testapi.prod.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapi.prod.model.ICollectionProductsRepo


/*
class ProductsViewModelFactory {
}

 */


class ProductsViewModelFactory (private val irepo: ICollectionProductsRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {

            ProductsViewModel(irepo) as T

        } else {

            throw IllegalArgumentException("ViewModel Class not found")

        }
    }
}