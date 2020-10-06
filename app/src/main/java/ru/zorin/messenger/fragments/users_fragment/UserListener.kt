package ru.zorin.messenger.fragments.users_fragment

import ru.zorin.messenger.model.Message
import ru.zorin.messenger.model.User

interface UserListener {
    fun showUsers(list:MutableList<User>)

    fun onClickUser(login:String,id:Int)

    fun openChat(dialogId:Int,friendLogin:String)

}