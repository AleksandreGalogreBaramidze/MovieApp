package com.example.movieapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import com.example.movieapp.utils.Constants.GOOGLE_URL
import java.net.URL

class InternetChecker(context: Context) : LiveData<Boolean?>() {
    private lateinit var callback: ConnectivityManager.NetworkCallback
    private val network: MutableSet<Network> = mutableSetOf()
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private fun validNetworkChecker() {
        postValue(network.size > 0)
    }

    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    private fun networkChecker() =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                val hasInternetConnectivityManager =
                    networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                if (hasInternetConnectivityManager == true) {
                    try {
                        network.openConnection(URL(GOOGLE_URL)).connect()
                        this@InternetChecker.network.add(network)
                        validNetworkChecker()
                    } catch (e: Exception) {
                        validNetworkChecker()
                    }
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }
        }

    override fun onActive() {
        super.onActive()
        callback = networkChecker()
        val networkRequest = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
        connectivityManager.registerNetworkCallback(networkRequest, callback)
    }

}