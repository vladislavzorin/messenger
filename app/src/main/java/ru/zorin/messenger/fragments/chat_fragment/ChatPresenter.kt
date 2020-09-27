package ru.zorin.messenger.fragments.chat_fragment

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.zorin.messenger.model.Message
import ru.zorin.messenger.repositories.ChatRepository

@InjectViewState
class ChatPresenter(var repository: ChatRepository): MvpPresenter<ChatView>(),ChatListener {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository.listener = this
        repository.getChatId()

        viewState.setSubTitle("Вы вошли как ${repository.getLogin()}")
    }

    fun getMessage(){
        viewState.showProgressBar()
        repository.loadChat()
    }

    fun sendMessage(message:String){
        repository.sendMessage(message)
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