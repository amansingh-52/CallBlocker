package com.example.callblocker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.callblocker.db.blocklist.EntryDatabase
import com.example.callblocker.db.blocklist.PhoneEntry
import com.example.callblocker.repository.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryViewModel(application: Application): AndroidViewModel(application) {

    private val repository: EntryRepository
    val allBlockedNumber: LiveData<List<PhoneEntry>>
    val getNumbers : LiveData<List<String>>


    init {
        val dao = EntryDatabase.getDatabase(application).entryDao()
        repository = EntryRepository(dao)
        allBlockedNumber = repository.allBlockedNumber
        getNumbers = repository.getNumbers
    }

    fun delete(phoneEntry: PhoneEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(phoneEntry)
    }

    fun insert(phoneEntry: PhoneEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.addNumber(phoneEntry)
    }

}