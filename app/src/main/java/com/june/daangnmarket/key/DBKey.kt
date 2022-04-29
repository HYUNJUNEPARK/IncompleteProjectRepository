package com.june.daangnmarket.key

class DBKey {
    companion object {
        const val TAG = "testLog"
        const val DB_ARTICLES = "Articles"
        const val DB_USERS = "DaangnUsers"
        //Articles
        const val CREATED_AT = "createdAt"
        const val IMAGE_URL = "imageUrl"
        const val PRICE = "price"
        const val SELLER_ID = "sellerId"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        //Chat
        const val CHILD_CHATROOM = "DaangnChatRoom"
        const val DB_CHATS = "ChatContents"
        //Intent
        const val ARTICLE_MODEL_INTENT = "model"
        const val CHAT_KEY = "chatKey"
    }
}