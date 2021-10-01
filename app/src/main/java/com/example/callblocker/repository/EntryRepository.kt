package com.example.callblocker.repository

import androidx.lifecycle.LiveData
import com.example.callblocker.db.blocklist.EntryDao
import com.example.callblocker.db.blocklist.PhoneEntry

class EntryRepository(private val entryDao: EntryDao) {

    val allBlockedNumber: LiveData<List<PhoneEntry>> = entryDao.getAllBlockedNumbers()
    val getNumbers : LiveData<List<String>> = entryDao.getNumbers()

    fun addNumber(phoneEntry: PhoneEntry){
        entryDao.insert(phoneEntry)
    }

    fun delete(phoneEntry: PhoneEntry){
        entryDao.delete(phoneEntry)
    }


}