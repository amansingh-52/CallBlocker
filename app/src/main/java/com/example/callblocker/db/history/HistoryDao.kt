package com.example.callblocker.db.history

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.callblocker.utils.Constants

@Dao
interface HistoryDao {

    @Query(value = "SELECT * FROM ${Constants.HISTORY_DATABASE}")
    fun getAllBlockedCalls() : LiveData<List<HistoryEntry>>

    @Insert
    fun insert(phoneEntry: HistoryEntry)

    @Delete
    fun delete(phoneEntry: HistoryEntry)
}