package com.example.testapi.prod.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testapi.*
import com.example.testapi.databinding.FragmentProductBinding
import com.example.testapi.models.brands.ApiState
import com.example.testapi.models.brands.RemoteSource
import com.example.testapi.models.productdetails.Product
import com.example.testapi.models.products.CollectProductsModel
import com.example.testapi.prod.model.CollectionProductsRepo
import com.example.testapi.prod.viewmodel.ProductsViewModel
import com.example.testapi.prod.viewmodel.ProductsViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment(),OnClickToShowDetails {
    private lateinit var productsBinding:FragmentProductBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var viewModel: ProductsViewModel
   // lateinit var myProducts: List<Product>
   private  var myProducts: List<Product> = emptyList()

    lateinit var networkObservation: NetworkObservation
    var ids = ""
    lateinit var filterList : List<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productsBinding = FragmentProductBinding.inflate(inflater)
        productsAdapter = ProductsAdapter(listOf(), this)
        val factory =
            ProductsViewModelFactory(CollectionProductsRepo(RemoteSource()))
        viewModel = ViewModelProvider(this, factory)[ProductsViewModel::class.java]
        return productsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataReceived = requireArguments().getLong("id")
       //   val dataReceived = 1063001337


        if(Constants.currencyType == "EGP"){
            productsBinding.section0150.text = "0 - 4500 EGP"
            productsBinding.section151300.text = "4500 - 9000 EGP"
        }

        viewModel.getCollectionProducts(dataReceived)
        productsBinding.productsRV.adapter = productsAdapter
        productsBinding.productsRV.layoutManager = GridLayoutManager(requireContext(), 2)
        filterList = listOf()
        updateRecycleView()
        searchForProduct()
        checkNetwork()
        productsBinding.section0150.setOnClickListener {
            chosePriceFiltration()
        }
        productsBinding.section151300.setOnClickListener {
            chosePriceFiltration()
        }
        productsBinding.sectionAll.setOnClickListener {
            chosePriceFiltration()
        }
    }
    private fun updateRecycleView() {
        lifecycleScope.launch {
            viewModel.collectionProducts.collect {
                when (it) {
                    is ApiState.Loading -> {
                        productsBinding.progressBar2.visibility = View.VISIBLE
                    }
                    is ApiState.Failure -> {
                        productsBinding.progressBar2.visibility = View.GONE
                        print(it.error)
                    }
                    is ApiState.Success<*> -> {
                        val collectionProduct = it.date as? CollectProductsModel
                        /*
                        if (collectionProduct != null) {
                            myProducts = collectionProduct.products
                            getFullProductDetails()
                        } else {
                            // Handle the case when collectionProduct is null
                        }

                         */
                    }
                    else -> {}
                }
            }
        }
    }


    fun getFullProductDetails() {
        myProducts.forEach { item -> ids += "${item.id}," }
        viewModel.getSelectedProducts(ids)
        ids = ""
        lifecycleScope.launch {
            viewModel.accessProductList.collect() { result ->
                when (result) {
                    is ApiState.Success<*> -> {
                        productsBinding.tvNoProduct.visibility=View.GONE
                        productsBinding.progressBar2.visibility = View.GONE
                        var apiProduct = result.date as List<Product>
                        myProducts = apiProduct
                        filterList =myProducts
                        productsAdapter.updateList(myProducts)
                    }
                    is ApiState.Failure -> {
                        //  homeBinding.progressBar.visibility = View.GONE

                    }
                    is ApiState.Loading -> {
                        //   homeBinding.progressBar.visibility = View.VISIBLE
                    }

                    else -> {}
                }

            }
        }
    }

    fun searchForProduct() {
        productsBinding.productSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    fun chosePriceFiltration(){
        when((productsBinding.priceRadioGroup.checkedRadioButtonId)){
            R.id.section0_150 ->{
                productsAdapter.updateList(filterProductsWithPrice(0,150))
            }
            R.id.section151_300 ->{
                productsAdapter.updateList(filterProductsWithPrice(150,300))

            }
            R.id.sectionAll ->{
                productsAdapter.updateList(myProducts)

            }

        }
        if (filterList.size==0 && !productsBinding.sectionAll.isChecked){
            productsBinding.tvNoProduct.visibility=View.VISIBLE
        }else{
            productsBinding.tvNoProduct.visibility=View.GONE

        }
    }

    fun filterProductsWithPrice(start: Int, end: Int): List<Product> {
        return myProducts?.filter {
            it.variants?.get(0)?.price?.toDouble()?.toInt() ?: 0 in start..end
        } ?: emptyList()
    }



    fun filterProducts(text: String) {
        var filtteredProducts = mutableListOf<Product>()
        if (checkTheButtonIsChecked()) {
            for (product in filterList) {
                if (product.title?.lowercase()?.contains(text.lowercase())!!) {
                    filtteredProducts.add(product)
                }

            }
            productsAdapter.updateList(filtteredProducts)
            if (filtteredProducts.isEmpty()) {
                productsBinding.tvNoProduct.visibility = View.VISIBLE
            } else {
                productsBinding.tvNoProduct.visibility = View.GONE

            }
        } else {
            for (product in myProducts) {
                if (product.title?.lowercase()?.contains(text.lowercase())!!) {
                    filtteredProducts.add(product)
                }

            }
            productsAdapter.updateList(filtteredProducts)
            if (filtteredProducts.isEmpty()) {
                productsBinding.tvNoProduct.visibility = View.VISIBLE
            } else {
                productsBinding.tvNoProduct.visibility = View.GONE

            }

        }
    }

    override fun ShowProductDetalis(productId: Long) {
       // val action = ProductsFragmentDirections.fromProductToDetails(productId)
       // Navigation.findNavController(requireView()).navigate(action)
    }

    fun checkNetwork() {
     //   networkObservation = NetworkConectivityObserver(requireContext())
        networkObservation = NetworkConnectivityObserver(requireContext())
        lifecycleScope.launch {
            networkObservation.observeOnNetwork().collectLatest {
                when (it.name) {
                    "Avaliavle" -> {
                        Log.i("Internet", it.name)
                        retry()
                    }
                    "Lost" -> {
                        showInternetDialog()
                    }
                    InternetStatus.UnAvailable.name -> {
                        Log.i("Internet", it.name)
                        showInternetDialog()
                    }
                }
            }
        }
    }

    private fun showInternetDialog() {

     //   (context as MainActivity).showSnakeBar()
    }

    fun retry() {
        val dataReceived = requireArguments().getLong("id")
        viewModel.getCollectionProducts(dataReceived)

    }


    fun checkTheButtonIsChecked() : Boolean {
        if(productsBinding.section0150.isChecked || productsBinding.section151300.isChecked){
            return true
        }
        return false
    }


}