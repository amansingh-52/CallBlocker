package com.example.callblocker.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import com.android.internal.telephony.ITelephony
import com.example.callblocker.R
import com.example.callblocker.ui.MainActivity
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
                EntryViewModel(application).getNumbers.observe(this) {
                    if (it.contains(ModelNumber.modelNumber(number))) {
                        telephonyService.endCall()
                        sendNotification(number)
                        Toast.makeText(this, "Ending the call from: $number", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onStartCommand(intent, flags, startId)

    }

    private fun sendNotification(number: String) {
        val mIntent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this,
            0,
            mIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_baseline_delete_forever_24)
                .setContentTitle("Call blocked")
                .setContentText(number)
                .setContentIntent(pIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)

        val notificationManagerCompat = NotificationManagerCompat.from(this)

        notificationManagerCompat.notify(1, builder.build())
    }


}