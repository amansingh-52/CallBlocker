package com.example.callblocker.db.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.callblocker.utils.Constants

@Entity(tableName = Constants.HISTORY_DATABASE)
data class HistoryEntry(
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "time") val time: String
){
    @PrimaryKey(autoGenerate = true) var uid: Int? = null
}