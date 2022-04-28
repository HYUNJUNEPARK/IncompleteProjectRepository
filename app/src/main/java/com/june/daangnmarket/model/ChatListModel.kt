package com.june.daangnmarket.model

data class ChatListModel(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: String,
    val imageUrl: String?
) {
    constructor(): this("", "", "", "", "")
}