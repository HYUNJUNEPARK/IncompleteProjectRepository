package com.june.daangnmarket.network

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.widget.Toast
import com.june.daangnmarket.dialog.UnConnectionDialog

class NetworkConnection(private val context: Context) : ConnectivityManager.NetworkCallback() {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()
    //lateinit var myDialog: AlertDialog


    private val unConnectionDialog: AlertDialog by lazy {
        AlertDialog.Builder(context)
            .setTitle("네트워크 연결 안됨")
            .setMessage("WIFE 또는 LTE 연결을 확인해주세요")
            .setPositiveButton("취소") { _, _ -> }
            .setNegativeButton("WIFI 연결") { _, _ ->
                connectWifi()
            }
            .create()
    }




    //NetworkCallback 등록
    fun register() {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    private fun getConnectivityStatus(): Network? {//연결된 네트워크가 없을 시 null 리턴
        val network: Network? = connectivityManager.activeNetwork ?: null
        return network
    }

    //콜백이 등록되거나 네트워크가 연결되었을 때 실행되는 메소드
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        if (getConnectivityStatus() == null) {
            Toast.makeText(context, "Network Null", Toast.LENGTH_SHORT).show()

//            val myDialog = UnConnectionDialog(context)
//            myDialog.unConnectionDialog.show()


            //unConnectionDialog.show()


        } else {
            unConnectionDialog.dismiss()


        }
    }

    //네트워크 끊겼을 때 실행되는 메소드
    override fun onLost(network: Network) {
        super.onLost(network)



        unConnectionDialog.show()



    }

    //NetworkCallback 해제
    fun unregister() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    private fun connectWifi() {
        val intent = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK)
        context.startActivity(intent)
    }
}