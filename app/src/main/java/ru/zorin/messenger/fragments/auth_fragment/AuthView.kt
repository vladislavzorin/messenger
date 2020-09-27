package ru.zorin.messenger.fragments.auth_fragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface AuthView : MvpView {

    @AddToEndSingle
    fun showMessage(msg:String)

    @OneExecution
    fun onLoginSuccess()

    @OneExecution
    fun onLoginError()
}