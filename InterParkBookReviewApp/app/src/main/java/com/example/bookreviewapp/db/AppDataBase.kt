package com.example.bookreviewapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bookreviewapp.dao.HistoryDAO
import com.example.bookreviewapp.dao.ReviewDao
import com.example.bookreviewapp.model.History
import com.example.bookreviewapp.model.Review

@Database(entities = [History::class, Review::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDAO
    abstract fun reviewDao(): ReviewDao
}

/*
DB 가 이미 있는데 새로운 dB 를 만든다면 충돌로 인해 앱이 크래쉬 될 수 있음 (version = 1)
Histroy로 db를 만든 후에 Review로 만들었을 때 발생함
version = 2로 바꾸고
추가된 Review 를 어떻게 처리할지(컬럼 추가로 해결) 담겨있는 마이그레이션 코드를 작성함

*/
fun getAppDatabase(context: Context): AppDataBase {
    val migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `REVIEW` (`id` INTEGER, `review` TEXT,"+"PRIMARY KEY(`id`))")
        }
    }

    return Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "BookSearchDB"
    ).addMigrations(migration_1_2)
     .build()
}