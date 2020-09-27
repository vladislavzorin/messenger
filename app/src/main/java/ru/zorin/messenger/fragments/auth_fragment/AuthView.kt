package ru.zorin.messenger.fragments.auth_fragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface AuthView : MvpView {

    @AddToEndSingle
    fun showMessage(msg:String)

    @AddToEndSingle
    fun onLoginSuccess()

    @AddToEndSingle
    fun onLoginError()
}