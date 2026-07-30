package com.onlineping.routing

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.onlineping.routing/vpn"
    private val VPN_REQUEST_CODE = 0x0F

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "startVpn" -> {
                    val intent = VpnService.prepare(this)
                    if (intent != null) {
                        startActivityForResult(intent, VPN_REQUEST_CODE)
                        result.success("REQUESTING_PERMISSION")
                    } else {
                        onActivityResult(VPN_REQUEST_CODE, Activity.RESULT_OK, null)
                        result.success("STARTED")
                    }
                }
                "stopVpn" -> {
                    val intent = Intent(this, OnlinePingVpnService::class.java)
                    intent.action = "STOP"
                    startService(intent)
                    result.success("STOPPED")
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VPN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val intent = Intent(this, OnlinePingVpnService::class.java)
            startService(intent)
        }
    }
}
