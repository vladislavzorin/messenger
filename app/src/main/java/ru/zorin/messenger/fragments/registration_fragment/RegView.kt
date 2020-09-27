package ru.zorin.messenger.fragments.registration_fragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface RegView : MvpView {

    @AddToEndSingle
    fun showMessage(msg:String)

    @AddToEndSingle
    fun onRegistrationError()

    @AddToEndSingle
    fun onRegistrationSuccess()
}