package com.example.bookreviewapp.model

import com.google.gson.annotations.SerializedName

data class BestSellerDTO(
    @SerializedName("title") val title : String,
    @SerializedName("item") val bookDetail : List<BookDetailDTO>
)
