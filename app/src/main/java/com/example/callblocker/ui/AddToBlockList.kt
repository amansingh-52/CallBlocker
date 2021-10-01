package com.example.callblocker.ui

import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.callblocker.R

class AddToBlockList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_block_list)
        setUpUI()
    }

    private fun setUpUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(getColor(R.color.primaryDarkColor)))
        }
    }
}