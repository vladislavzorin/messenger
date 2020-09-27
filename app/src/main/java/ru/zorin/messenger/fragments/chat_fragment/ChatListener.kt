package ru.zorin.messenger.fragments.chat_fragment

import ru.zorin.messenger.model.Message

interface ChatListener {
    fun showChat(list:MutableList<Message>)
}