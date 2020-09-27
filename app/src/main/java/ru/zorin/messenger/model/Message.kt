package ru.zorin.messenger.model

data class Message(val messege: String,
                   val username: String,
                   val userId: Int,
                   val time:String)