package com.example.callblocker.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast


class IncomingCallReceiver : BroadcastReceiver() {


    @SuppressLint("SoonBlockedPrivateApi")
    override fun onReceive(context: Context, intent: Intent) {
        try {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING, ignoreCase = true)) {
                try {
                    if (number != null) {
                        val mIntent = Intent(context, CallRejectService::class.java)
                        mIntent.putExtra(Constants.PHONE_NUMBER, number)
                        context.startService(mIntent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                
                Toast.makeText(context, "Ring $number", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

