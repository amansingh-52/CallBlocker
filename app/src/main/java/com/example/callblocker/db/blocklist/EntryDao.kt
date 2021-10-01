package com.example.callblocker.db.blocklist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.callblocker.utils.Constants

@Dao
interface EntryDao {

    @Query(value = "SELECT * FROM ${Constants.DATABASE_NAME}")
    fun getAllBlockedNumbers() : LiveData<List<PhoneEntry>>

    @Query(value = "SELECT number FROM ${Constants.DATABASE_NAME}")
    fun getNumbers() : LiveData<List<String>>

    @Insert
    fun insert(phoneEntry: PhoneEntry)

    @Delete
    fun delete(phoneEntry: PhoneEntry)

}