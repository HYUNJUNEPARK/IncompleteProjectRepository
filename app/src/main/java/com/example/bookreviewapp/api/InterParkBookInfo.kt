package com.example.bookreviewapp.api

import com.example.bookreviewapp.model.BestSellerDTO
import com.example.bookreviewapp.model.SearchBookDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InterParkBookInfo {
    @GET("api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword : String
    ) : Call<SearchBookDTO>

    @GET("api/bestSeller.api?output=json&categoryId=100")
    fun getBestSellerBooks(
        @Query("key") apiKey: String,
    ) : Call<BestSellerDTO>
}
