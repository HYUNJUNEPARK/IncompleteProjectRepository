package com.example.bookreviewapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookreviewapp.dao.HistoryDAO
import com.example.bookreviewapp.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO
}