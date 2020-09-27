package ru.zorin.messenger.fragments.chat_fragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.zorin.messenger.model.Message

interface ChatView : MvpView {

    @AddToEndSingle
    fun showChat(list:List<Message>)

    @AddToEndSingle
    fun scrollToLastPosition()

    @AddToEndSingle
    fun showProgressBar()

    @AddToEndSingle
    fun closeProgressBar()

    @AddToEndSingle
    fun setSubTitle(str:String)

}