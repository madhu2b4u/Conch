package com.everest.cloud.conch.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class NetworkListener : ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableStateFlow(false)
    private val validNetworks: MutableSet<Network> = HashSet()
    private lateinit var connectivityManager: ConnectivityManager

    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {

        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkChangeFilter = NetworkRequest.Builder()
        networkChangeFilter.addCapability(NET_CAPABILITY_INTERNET)

        connectivityManager.registerNetworkCallback(networkChangeFilter.build(), this)

        var isConnected = false

        connectivityManager.allNetworks.forEach { network ->
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.let {
                if (it.hasCapability(NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                }
                return@forEach
            }
        }
        isNetworkAvailable.value = isConnected
        return isNetworkAvailable
    }


    override fun onAvailable(network: Network) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
        if (hasInternetCapability == true) {
            // check if this network actually has internet
            CoroutineScope(Dispatchers.IO).launch {
                val hasInternet = execute(network.socketFactory)
                if (hasInternet) {
                    withContext(Dispatchers.Main) {
                        validNetworks.add(network)
                        checkValidNetwork()
                    }
                }
            }
        }
    }

    private fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            true
        } catch (e: IOException) {
            false
        }
    }

    private fun checkValidNetwork() {
        isNetworkAvailable.value = validNetworks.size > 0
    }

    override fun onLost(network: Network) {
        validNetworks.remove(network)
        checkValidNetwork()
    }
}