package ru.zorin.messenger.fragments.chat_fragment

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.zorin.messenger.model.Message
import ru.zorin.messenger.repositories.ChatRepository

@InjectViewState
class ChatPresenter(var repository: ChatRepository): MvpPresenter<ChatView>(),ChatListener {
    var dialogId:Int = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository.listener = this
        repository.getChatId()
    }

    fun getMessage(dialogId:Int){
        this.dialogId = dialogId
        viewState.showProgressBar()
        repository.loadChat(dialogId)
    }

    fun sendMessage(message:String){
        repository.sendMessage(message,dialogId)
        viewState.scrollToLastPosition()
    }

    override fun showChat(list: MutableList<Message>) {
        viewState.closeProgressBar()
        viewState.showChat(list)
        viewState.scrollToLastPosition()
    }

    fun logOut(){
        repository.logOut()
    }
}