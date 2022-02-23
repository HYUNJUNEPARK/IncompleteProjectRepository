package com.example.bookreviewapp.model

import com.google.gson.annotations.SerializedName

data class SearchBookDTO(
    @SerializedName("title") val title : String,
    @SerializedName("item") val bookDetails : List<BookDetailDTO>
)
