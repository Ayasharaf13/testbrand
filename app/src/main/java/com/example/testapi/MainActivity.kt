package com.example.testapi




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.databinding.ActivityMainBinding
import com.example.testapi.home.model.HomeRepo
import com.example.testapi.home.view.BrandsAdapter
import com.example.testapi.home.viewmodel.HomeViewModel
import com.example.testapi.home.viewmodel.HomeViewModelFactory
import com.example.testapi.models.brands.ApiState
import com.example.testapi.models.brands.BrandList
import com.example.testapi.models.brands.RemoteSource
import com.example.testapi.models.brands.SmartCollection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var homeBinding: ActivityMainBinding
    private lateinit var brandsAdapter: BrandsAdapter



    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var smartCollections: List<SmartCollection>
    lateinit var networkObservation: NetworkObservation




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        setupViews()

        // Initialize homeViewModelFactory
        val homeRepo = HomeRepo(RemoteSource())
        homeViewModelFactory = HomeViewModelFactory(homeRepo)

        // Initialize homeViewModel using ViewModelProvider
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        checkNetwork()
        setBrandData()
        homeViewModel.getBrands()
    }

    private fun setupViews() {
        brandsAdapter = BrandsAdapter(listOf())
        homeBinding.brandsRV.adapter = brandsAdapter
        homeBinding.brandsRV.layoutManager =
            GridLayoutManager(this@MainActivity, 2, RecyclerView.HORIZONTAL, false)
    }



    private fun checkNetwork() {
        networkObservation = NetworkConectivityObserver(this@MainActivity)
        lifecycleScope.launch {

            networkObservation.observeOnNetwork().collectLatest { networkStatus ->
                when (networkStatus) {
                   // InternetStatus.Available -> {
                    InternetStatus.Avaliavle->{
                        Log.i("Internet", "Available")
                        retry()
                    }

                    InternetStatus.Lost -> {
                        Log.i("Internet", "Lost")
                       // showInternetDialog()
                    }
                    InternetStatus.UnAvailable -> {
                        Log.i("Internet", "Unavailable")
                       // showInternetDialog()
                    }
                }
            }
        }
    }



    private fun retry() {
        homeViewModel.getBrands()
    }

    private fun setBrandData() {
        lifecycleScope.launch {
            homeViewModel.accessBrandsList.collect { result ->
                when (result) {
                    is ApiState.Success<*> -> {
                        homeBinding.progressBar.visibility = View.GONE
                    //    val brands = result.data as BrandList?
                        val brands=result.date as BrandList?
                        smartCollections = brands?.smart_collections ?: listOf()
                        brandsAdapter.setBrandsList(smartCollections)
                    }
                    is ApiState.Failure -> {
                        homeBinding.progressBar.visibility = View.GONE
                        // Handle the error if needed
                    }
                    is ApiState.Loading -> {
                        homeBinding.progressBar.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }
    }

}

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        // Initialize the adapter and handler here if needed
        // brandsAdapter = BrandsAdapter()
        // handler = Handler()

        // Initialize ViewModel and observe LiveData if required
        smartCollections = listOf()
        homeViewModelFactory = HomeViewModelFactory(HomeRepo(RemoteSource()))
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }
}

 */