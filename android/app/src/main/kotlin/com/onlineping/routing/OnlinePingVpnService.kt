package com.onlineping.routing

import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.util.Log

class OnlinePingVpnService : VpnService() {

    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action == "STOP") {
            stopVpn()
            return START_NOT_STICKY
        }

        startVpn()
        return START_STICKY
    }

    private fun startVpn() {
        try {
            if (vpnInterface == null) {
                val builder = Builder()
                builder.setSession("OnlinePingVpn")
                    .addAddress("10.0.0.2", 24)
                    .addRoute("0.0.0.0", 0)
                    .addDnsServer("8.8.8.8")
                    .setMtu(1500)

                vpnInterface = builder.establish()
                Log.d("OnlinePingVPN", "VPN Service Connected Successfully")
            }
        } catch (e: Exception) {
            Log.e("OnlinePingVPN", "Error starting VPN service: ${e.message}")
        }
    }

    private fun stopVpn() {
        try {
            vpnInterface?.close()
            vpnInterface = null
            stopSelf()
            Log.d("OnlinePingVPN", "VPN Service Stopped")
        } catch (e: Exception) {
            Log.e("OnlinePingVPN", "Error stopping VPN service: ${e.message}")
        }
    }

    override fun onDestroy() {
        stopVpn()
        super.onDestroy()
    }
}
