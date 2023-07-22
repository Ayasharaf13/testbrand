package com.example.testapi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


/*
class NetworkConectivityObserver {
}

 */
interface NetworkObservation {
    fun observeOnNetwork () : Flow<InternetStatus>

}
enum class InternetStatus {
    Avaliavle , Lost, UnAvailable
}
class NetworkConnectivityObserver(private val context: Context) : NetworkObservation {

    private val connectivityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                updateNetworkStatus()
            }
        }
    }

    private var connectivityStatusFlow = MutableStateFlow<InternetStatus>(InternetStatus.UnAvailable)

    init {
        context.registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        updateNetworkStatus()
    }

    private fun updateNetworkStatus() {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isAvailable = networkInfo?.isConnected ?: false
        val status = if (isAvailable) InternetStatus.Avaliavle else InternetStatus.Lost
        connectivityStatusFlow.value = status
    }

    override fun observeOnNetwork(): Flow<InternetStatus> = connectivityStatusFlow

    fun release() {
        context.unregisterReceiver(connectivityReceiver)
    }



}

/*
class NetworkConectivityObserver(private val context: Context) : NetworkObservation {

    private val connectivityManger = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun observeOnNetwork(): Flow<InternetStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(InternetStatus.Avaliavle) }
                }
                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(InternetStatus.Lost) }
                }
                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(InternetStatus.UnAvailable) }
                }
            }
            connectivityManger.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManger.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()

    }
}
interface NetworkObservation {
    fun observeOnNetwork () : Flow<InternetStatus>

}
enum class InternetStatus {
    Avaliavle , Lost, UnAvailable
}

 */