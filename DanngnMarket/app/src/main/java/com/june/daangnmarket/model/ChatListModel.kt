package com.june.daangnmarket.model

data class ChatListModel(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: String,
    val imageUrl: String?,
    val createAt: Long
) {
    constructor(): this("", "", "", "", "", 0)
}