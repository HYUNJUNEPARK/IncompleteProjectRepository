package com.june.daangnmarket.home

import java.io.Serializable


data class ArticleModel(
    val createdAt: Long?,
    val imageUrl: String?,
    val price: String?,
    val sellerId: String?,
    val title: String?,
    val description: String?
): Serializable {
    constructor(): this(0, "", "", "", "", "")
}