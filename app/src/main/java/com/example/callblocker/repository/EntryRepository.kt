package com.example.callblocker.repository

import androidx.lifecycle.LiveData
import com.example.callblocker.db.EntryDao
import com.example.callblocker.db.PhoneEntry

class EntryRepository(private val entryDao: EntryDao) {

    val allBlockedNumber: LiveData<List<PhoneEntry>> = entryDao.getAllBlockedNumbers()

    fun addNumber(phoneEntry: PhoneEntry){
        entryDao.insert(phoneEntry)
    }

    fun delete(phoneEntry: PhoneEntry){
        entryDao.delete(phoneEntry)
    }


}