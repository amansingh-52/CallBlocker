package com.example.callblocker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.callblocker.R
import com.example.callblocker.utils.Constants
import com.example.callblocker.utils.MyApplication
import com.example.callblocker.viewmodel.EntryViewModel


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: EntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUI()
        checkForPermission()
        initObserver()
        setOnClick()
    }

    private fun setUpUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(getColor(R.color.primaryDarkColor)))
        }
    }

    private fun setOnClick() {
        findViewById<Button>(R.id.add_random).setOnClickListener {
            startActivity(Intent(this, AddToBlockList::class.java))
        }
    }

    private fun initObserver() {
        viewModel = EntryViewModel(application)
        viewModel.allBlockedNumber.observe(this) {
            findViewById<TextView>(R.id.display).text = it.size.toString()
        }
    }

    private fun checkForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    Manifest.permission.READ_CALL_LOG
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG
                )
                requestPermissions(permissions, Constants.PERMISSION_REQUEST_READ_PHONE_STATE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.PERMISSION_REQUEST_READ_PHONE_STATE -> {
                (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                return
            }
        }
    }

}