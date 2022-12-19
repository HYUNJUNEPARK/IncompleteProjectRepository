package com.june.daangnmarket.model

data class ChatListItemModel(
  val buyerId: String,
  val sellerId: String,
  val itemTitle: String,
  val key: String
) {
    constructor(): this("", "", "", "")
}
