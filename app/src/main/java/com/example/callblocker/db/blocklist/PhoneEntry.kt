package com.example.callblocker.db.blocklist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.callblocker.utils.Constants

@Entity(tableName = Constants.DATABASE_NAME)
data class PhoneEntry(
    @ColumnInfo(name = "number") val number: String
){
    @PrimaryKey(autoGenerate = true) var uid: Int? = null
}