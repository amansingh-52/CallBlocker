package com.example.callblocker.repository

import androidx.lifecycle.LiveData
import com.example.callblocker.db.history.HistoryDao
import com.example.callblocker.db.history.HistoryEntry

class HistoryRepository(private val historyDao: HistoryDao) {
    val allBlockedCalls : LiveData<List<HistoryEntry>> = historyDao.getAllBlockedCalls()

    fun addEntry(historyEntry: HistoryEntry){
        historyDao.insert(historyEntry)
    }
}