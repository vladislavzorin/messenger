package ru.zorin.messenger.fragments.auth_fragment

interface AuthListener {

    fun onLoginSuccess(login:String,id:Int)
    fun onLoginError()
}