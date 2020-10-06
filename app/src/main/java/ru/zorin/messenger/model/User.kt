package ru.zorin.messenger.model

data class User(val login: String, val userId: Int, val lastMessage:Message)