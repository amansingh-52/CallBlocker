package com.example.callblocker.utils

import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.callblocker.viewmodel.EntryViewModel

open class BaseActivity : AppCompatActivity(){

    companion object{
        lateinit var viewModel: EntryViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
        ).get(EntryViewModel(application)::class.java)

    }
}