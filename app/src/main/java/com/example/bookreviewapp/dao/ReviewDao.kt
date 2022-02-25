package com.example.bookreviewapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookreviewapp.model.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review WHERE id == :id")
    fun getOneReview(id: Int): Review

    @Insert(onConflict = OnConflictStrategy.REPLACE) //충돌발생하면 새로운 것이 대체되어 저장됨
    fun saveReview(review: Review)
}
