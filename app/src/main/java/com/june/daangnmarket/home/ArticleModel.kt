package com.june.daangnmarket.home

data class ArticleModel(
    val createdAt: Long,
    val imageUrl: String,
    val price: String,
    val sellerId: String?,
    val title: String
) {
    constructor(): this(0, "", "", "", "")
}