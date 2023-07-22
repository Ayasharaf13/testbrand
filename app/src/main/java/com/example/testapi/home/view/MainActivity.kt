package com.example.testapi.home.view




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.InternetStatus
import com.example.testapi.NetworkConnectivityObserver
import com.example.testapi.NetworkObservation
import com.example.testapi.R
import com.example.testapi.databinding.ActivityMainBinding
import com.example.testapi.home.model.HomeRepo
import com.example.testapi.home.viewmodel.HomeViewModel
import com.example.testapi.home.viewmodel.HomeViewModelFactory
import com.example.testapi.models.brands.ApiState
import com.example.testapi.models.brands.BrandList
import com.example.testapi.models.brands.RemoteSource
import com.example.testapi.models.brands.SmartCollection
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/*
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}

 */

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(  R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

    }
}
