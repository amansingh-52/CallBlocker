package com.example.callblocker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.callblocker.utils.Constants

@Database(entities = [PhoneEntry::class], version = 1, exportSchema = false)
abstract class EntryDatabase: RoomDatabase() {
    abstract fun entryDao(): EntryDao
    companion object{
        @Volatile
        private var  INSTANCE: EntryDatabase? = null

        fun getDatabase(context: Context): EntryDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntryDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}