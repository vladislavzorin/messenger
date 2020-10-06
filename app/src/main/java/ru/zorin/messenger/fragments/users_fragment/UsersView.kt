package ru.zorin.messenger.fragments.users_fragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.zorin.messenger.model.Message
import ru.zorin.messenger.model.User

interface UsersView : MvpView {

    @AddToEndSingle
    fun showUsers(list:List<User>)

    @OneExecution
    fun openChat(dialogId:Int,friendLogin:String)

    @OneExecution
    fun showProgressBar()

    @OneExecution
    fun closeProgressBar()

    @AddToEndSingle
    fun setSubTitle(title:String)

}