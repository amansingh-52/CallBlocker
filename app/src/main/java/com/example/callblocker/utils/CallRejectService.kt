package com.example.callblocker.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleService
import com.android.internal.telephony.ITelephony
import com.example.callblocker.db.PhoneEntry
import com.example.callblocker.viewmodel.EntryViewModel
import java.lang.reflect.Method


class CallRejectService : LifecycleService() {

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    @SuppressLint("SoonBlockedPrivateApi")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val telephonyService: ITelephony
        val number = intent?.getStringExtra(Constants.PHONE_NUMBER)
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            val m: Method = tm.javaClass.getDeclaredMethod("getITelephony")
            m.isAccessible = true
            telephonyService = m.invoke(tm) as ITelephony
            if (number != null) {
                EntryViewModel(application).allBlockedNumber.observe(this) {
                   if(it.contains(PhoneEntry(number))){
                       telephonyService.endCall()
                       Toast.makeText(this, "Ending the call from: $number", Toast.LENGTH_SHORT)
                           .show()
                   }else{
                       Log.e("CalledOR", "HERE")
                   }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onStartCommand(intent, flags, startId)

    }

    override fun onCreate() {
        super.onCreate()
        EntryViewModel(application).allBlockedNumber.observe(this) {
            Log.e("CalledOR", "onCreate")
            Log.e("CalledOR", it.size.toString())
        }
    }

}