package ru.zorin.messenger.fragments.users_fragment

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.zorin.messenger.model.Message
import ru.zorin.messenger.model.User
import ru.zorin.messenger.repositories.ChatRepository

@InjectViewState
class UsersPresenter(var repository: ChatRepository) : MvpPresenter<UsersView>(),UserListener {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository.userListener = this
        repository.getDialogId()

    }

    fun loadUsers(){
        viewState.showProgressBar()
        repository.loadUsers()
    }

    override fun showUsers(list: MutableList<User>) {
       viewState.closeProgressBar()
       viewState.showUsers(list)
    }

    override fun onClickUser(login: String, id: Int) {
        repository.checkDialog(login)
    }

    override fun openChat(dialogId: Int,friendLogin:String) {
        viewState.openChat(dialogId,friendLogin)
    }

    fun logOut(){
        repository.logOut()
    }

    fun setSubTitle(){
        viewState.setSubTitle("Вы вошли как ${repository.getLogin()}")
    }

    fun getLogin():String{
        return repository.getLogin()
    }
}