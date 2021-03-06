package ru.zorin.messenger.fragments.chat_fragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.zorin.messenger.model.Message

interface ChatView : MvpView {

    @OneExecution
    fun showChat(list:List<Message>)

    @OneExecution
    fun scrollToLastPosition()

    @OneExecution
    fun showProgressBar()

    @OneExecution
    fun closeProgressBar()

    @AddToEndSingle
    fun setSubTitle(str:String)

}