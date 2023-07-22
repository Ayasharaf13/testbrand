package com.example.testapi.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.InternetStatus
import com.example.testapi.NetworkConnectivityObserver
import com.example.testapi.NetworkObservation
import com.example.testapi.R
import com.example.testapi.databinding.FragmentHomeBinding
import com.example.testapi.home.model.HomeRepo
import com.example.testapi.home.viewmodel.HomeViewModel
import com.example.testapi.home.viewmodel.HomeViewModelFactory
import com.example.testapi.models.brands.ApiState
import com.example.testapi.models.brands.BrandList
import com.example.testapi.models.brands.RemoteSource
import com.example.testapi.models.brands.SmartCollection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

 private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var brandsAdapter: BrandsAdapter


    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var smartCollections: List<SmartCollection>
    lateinit var networkObservation: NetworkObservation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        smartCollections = listOf()
        homeViewModelFactory =
            HomeViewModelFactory(HomeRepo(RemoteSource()))
        homeViewModel = ViewModelProvider(
            this,
            homeViewModelFactory
        ).get(HomeViewModel::class.java)
        homeBinding = FragmentHomeBinding.inflate(inflater)


        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetwork()
        brandsAdapter = BrandsAdapter(listOf())
        homeBinding.brandsRV.adapter = brandsAdapter

        homeBinding.brandsRV.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false)
        setBrandData()
        homeViewModel.getBrands()
       // searchForBrands()
    }





    fun checkNetwork() {
       // networkObservation = NetworkConectivityObserver(requireContext())
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
                    InternetStatus.UnAvailable.name-> {
                        Log.i("Internet", it.name)
                        showInternetDialog()
                    }
                }
            }
        }
    }

    private fun showInternetDialog() {
      //  (context as MainActivity).showSnakeBar()

    }

    fun retry() {

        homeViewModel.getBrands()

    }


/*
    private fun setUpTarnsformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            page.scaleY = 0.85f
        }
        viewPager2.setPageTransformer(transformer)
    }

 */

    fun setBrandData() {
        lifecycleScope.launch {
            homeViewModel.accessBrandsList.collect() { result ->
                when (result) {
                    is ApiState.Success<*> -> {
                        homeBinding.progressBar.visibility = View.GONE
                        homeBinding.tbNoBrands.visibility = View.GONE
                        var brands = result.date as BrandList?
                        smartCollections = brands?.smart_collections ?: listOf()
                        brandsAdapter.setBrandsList(smartCollections)
                    }
                    is ApiState.Failure -> {
                        homeBinding.progressBar.visibility = View.GONE

                    }
                    is ApiState.Loading -> {
                        homeBinding.progressBar.visibility = View.VISIBLE
                    }

                    else -> {}
                }

            }
        }
    }





    fun filterBrands(text: String) {
        var filterdBrands = mutableListOf<SmartCollection>()
        for (brand in smartCollections) {
            if (brand.title.lowercase().contains(text.lowercase())) {
                filterdBrands.add(brand)
            }

        }
        brandsAdapter.setBrandsList(filterdBrands)
        if (filterdBrands.isEmpty()) {
            homeBinding.tbNoBrands.visibility = View.VISIBLE
            }else{
            homeBinding.tbNoBrands.visibility = View.GONE

        }

    }




}