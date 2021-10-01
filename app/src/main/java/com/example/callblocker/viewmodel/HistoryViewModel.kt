package com.example.callblocker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.callblocker.db.history.HistoryDatabase
import com.example.callblocker.db.history.HistoryEntry
import com.example.callblocker.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HistoryRepository
    val allBlockedCalls: LiveData<List<HistoryEntry>>

    init {
        val dao = HistoryDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(dao)
        allBlockedCalls = repository.allBlockedCalls
    }

    fun insert(historyEntry: HistoryEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.addEntry(historyEntry)
    }
}